package com.bit.shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
    private Long itemId;
    private String itemName;
    private Long cnt;
}
