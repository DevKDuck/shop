package com.devkduck.duckshop.dto;

import com.devkduck.duckshop.entity.ItemImg;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

/*
상품 저장후 상품 이미지에 대한 데이터를 전달할 클래스
 */
@Getter
@Setter
public class ItemImgDto {
    private Long id;

    @Column(length = 1024)
    private String imgName;

    private String oriImgName;

    @Column(length = 1024)
    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
