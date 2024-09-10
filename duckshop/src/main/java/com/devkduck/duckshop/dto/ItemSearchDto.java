package com.devkduck.duckshop.dto;

import com.devkduck.duckshop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
    private String searchDateType;
    /*
    현재 시간과 상품 등록일 비교하여 상품 데이터 조회
    all(전체), 1d(최근 하루동안), 1w(최근 일주일), 1m(한달), 6m(6개월 옹안 )
     */
    private ItemSellStatus searchSellStatus; // 판매 상태 기준 조회
    private String searchBy; // 어떤 유형으로 조회할지 (itemNm: 상품명, createdBy: 등록자 아이디)
    private String searchQuery = ""; //조회할 검색어 저장 변수
}
