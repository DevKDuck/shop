package com.devkduck.duckshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //하나의 상품은 여러 주문 상품에 들어갈 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //한번의 주문은 여러개의 상품을 주문할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //주문 가격
    private int orderPrice;

    private int count;

    public static OrderItem createdOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    //주문 취소시 주문 수량 만큼 상품 재고 더해줌
    public void cancel(){
        this.getItem().addStock(count);
    }
}
