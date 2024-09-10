package com.devkduck.duckshop.repository;

import com.devkduck.duckshop.dto.ItemSearchDto;
import com.devkduck.duckshop.dto.MainItemDto;
import com.devkduck.duckshop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    //pageable = itemSearchDto + 페이징 정보
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
