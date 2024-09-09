package com.devkduck.duckshop.dto;

import com.devkduck.duckshop.constant.ItemSellStatus;
import com.devkduck.duckshop.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotBlank(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "설명은 필수 입력 값입니다.")
    private String itemDetail;

    @NotBlank(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //상품 저장 후 수정시 상품 이미지 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); //상품 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }


}
