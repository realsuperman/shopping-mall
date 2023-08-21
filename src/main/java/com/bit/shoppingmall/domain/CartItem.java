package com.bit.shoppingmall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
@Getter
public class CartItem {
    private Long itemId;
    private Long itemQuantity;
    private Long cartId;
    private Long consumerId;
}
