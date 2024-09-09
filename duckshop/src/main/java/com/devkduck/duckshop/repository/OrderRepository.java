package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
