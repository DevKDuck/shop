package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    //카트 + 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
