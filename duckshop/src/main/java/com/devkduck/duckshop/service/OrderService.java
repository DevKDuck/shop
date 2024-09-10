package com.devkduck.duckshop.service;

import com.devkduck.duckshop.dto.OrderDto;
import com.devkduck.duckshop.entity.Item;
import com.devkduck.duckshop.entity.Member;
import com.devkduck.duckshop.entity.Order;
import com.devkduck.duckshop.entity.OrderItem;
import com.devkduck.duckshop.repository.ItemRepository;
import com.devkduck.duckshop.repository.MemberRepository;
import com.devkduck.duckshop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email){
        //주문 상품 조회
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        //로그인 이메일 이용하여 회원 정보 조회
        Member member = memberRepository.findByEmail(email);

        //주문할 상품 엔티티와 수량 이용하여 주문 상품 엔티티 생성
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createdOrderItem(item,orderDto.getCount());
        orderItemList.add(orderItem);

        //회원 정보와 주문 상품 리스트 정보 이용하여 주문 엔티티 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }
}
