package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

//      인자값으로 들어온 상품명 찾기
      List<Item> findByItemNm(String itemNm);

    // 상품명과 상품 설명을 이용하여 조회
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메서드
    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
}
