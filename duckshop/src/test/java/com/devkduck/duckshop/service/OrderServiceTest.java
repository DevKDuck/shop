package com.devkduck.duckshop.service;


import com.devkduck.duckshop.constant.ItemSellStatus;
import com.devkduck.duckshop.dto.OrderDto;
import com.devkduck.duckshop.entity.Item;
import com.devkduck.duckshop.entity.Member;
import com.devkduck.duckshop.entity.Order;
import com.devkduck.duckshop.entity.OrderItem;
import com.devkduck.duckshop.repository.ItemRepository;
import com.devkduck.duckshop.repository.MemberRepository;
import com.devkduck.duckshop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);

    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();

        //주문 상품의 총 가격과 데이터베이스에 저장된 상품의 가격을 비교하여 성공적이어야 테스트 성공
        assertEquals(totalPrice, order.getTotalPrice());
    }

//    @Test
//    @DisplayName("주문 취소 테스트")
//    public void cancelOrder(){
//        Item item = saveItem();
//        Member member = saveMember();
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setCount(10);
//        orderDto.setItemId(item.getId());
//        Long orderId = orderService.order(orderDto, member.getEmail());
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//        orderService.cancelOrder(orderId);
//
//        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
//        assertEquals(100, item.getStockNumber());
//    }

}