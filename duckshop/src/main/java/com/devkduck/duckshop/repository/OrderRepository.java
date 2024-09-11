package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.member.email = :email order by o.orderDate desc")

    //현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o where o.member.email = :email")

    //현재 로그인한 회원의 주문 개수가 몇 개인지 조회
    Long countOrders(@Param("email") String email);
}
