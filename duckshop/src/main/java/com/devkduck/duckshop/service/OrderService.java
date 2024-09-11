package com.devkduck.duckshop.service;

import com.devkduck.duckshop.dto.OrderDto;
import com.devkduck.duckshop.dto.OrderHistDto;
import com.devkduck.duckshop.dto.OrderItemDto;
import com.devkduck.duckshop.entity.*;
import com.devkduck.duckshop.repository.ItemImgRepository;
import com.devkduck.duckshop.repository.ItemRepository;
import com.devkduck.duckshop.repository.MemberRepository;
import com.devkduck.duckshop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final ItemImgRepository itemImgRepository;

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

    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        //이메일과 페이징 조건을 이용하여 주문 목록 조회
        List<Order> orders = orderRepository.findOrders(email, pageable);
        //유저 총 주문 개수
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //주문 리스트 순회하면서 구매 이력 페이지에 전달할 DTO todtjd
        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            //주문한 상품 대표 이미지 조회
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(),"Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItem(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }
        //페이지 구현 객체 생성
        return new PageImpl<>(orderHistDtos, pageable, totalCount);
    }
}
