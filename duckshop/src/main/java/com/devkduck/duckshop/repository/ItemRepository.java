package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

//      인자값으로 들어온 상품명 찾기
      List<Item> findByItemNm(String itemNm);

    // 상품명과 상품 설명을 이용하여 조회
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메서드
    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);


    //@Query를 이용한 검색 처리
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    //nativeQuery를 이용하여 검색
    @Query(value="select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);
}
