package com.devkduck.duckshop.service;

import com.devkduck.duckshop.dto.CartDetailDto;
import com.devkduck.duckshop.dto.CartItemDto;
import com.devkduck.duckshop.dto.CartOrderDto;
import com.devkduck.duckshop.dto.OrderDto;
import com.devkduck.duckshop.entity.Cart;
import com.devkduck.duckshop.entity.CartItem;
import com.devkduck.duckshop.entity.Item;
import com.devkduck.duckshop.entity.Member;
import com.devkduck.duckshop.repository.CartItemRepository;
import com.devkduck.duckshop.repository.CartRepository;
import com.devkduck.duckshop.repository.ItemRepository;
import com.devkduck.duckshop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email) {

        //장바구니에 담을 상품 조회
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        //현재 로그인한 회원 조회
        Member member = memberRepository.findByEmail(email);

        //로그인한 회원의 장바구니 조회
        Cart cart = cartRepository.findByMemberId(member.getId());

        //상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티 생성
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        //현재 상품이 장바구니에 이미 들어가 있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());

        //장바구니에 이미 있던 상품일 경우 기존 수량에 추가
        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        }
        else{
            //장바구니에 상품이 없을 경우 장바구니, 상품, 수량이용하여 장바구니 들어갈 상품 저장
           CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
           cartItemRepository.save(cartItem);
           return cartItem.getId();
        }
    }

    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        //장바구니에 상품을 한번도 안 담았을 경우 비어 있는 리스트 반환
        if(cart == null){
            return cartDetailDtoList;
        }

        //장바구니 담겨있는 상품 조회
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }

    //현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를 경우 false , 같으면 true
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    //장바구니 수량 업데이트
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            //주문 후 장바구니에서 제거
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}
