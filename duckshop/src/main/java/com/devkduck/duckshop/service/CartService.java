package com.devkduck.duckshop.service;

import com.devkduck.duckshop.dto.CartItemDto;
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

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

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
}
