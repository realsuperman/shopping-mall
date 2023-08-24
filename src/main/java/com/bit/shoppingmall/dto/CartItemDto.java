package com.bit.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CartItemDto {
    private Long itemId;
    private Long categoryId;
    private String itemName;
    private Long itemPrice;
    private String itemImagePath;
    private Long totalPrice;
    private Long itemQuantity;
    private Long cartId;
}
