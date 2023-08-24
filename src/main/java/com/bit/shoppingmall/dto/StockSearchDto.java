package com.bit.shoppingmall.dto;

import lombok.Setter;

@Setter
public class StockSearchDto {
    String itemName;
    Long pageSize;
    Long offset;
}
