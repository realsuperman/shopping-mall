package com.bit.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockStatDto {
    private Long cargoId;
    private Long statusId;
}