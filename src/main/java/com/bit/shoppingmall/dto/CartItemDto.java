package com.bit.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CartItemDto {
    private Long itemId;
    private Long categoryId;
    private String itemName;
    private Long itemPrice;
    private String itemImagePath;
}
