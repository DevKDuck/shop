package com.devkduck.duckshop.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;

    @Column(columnDefinition = "nvarchar(max)")
    private String itemNm;

    private Integer price;

    @Column(columnDefinition = "nvarchar(max)")
    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
