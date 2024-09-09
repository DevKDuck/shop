package com.devkduck.duckshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem {
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

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
