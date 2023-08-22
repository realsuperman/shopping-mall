package com.bit.shoppingmall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Getter
public class CartItem {
    private Long itemId;
    private Long itemQuantity;
    private String cartId;
    private Long consumerId;

    public void increaseQuantity(Long itemQuantity) {
        this.itemQuantity += itemQuantity;
    }
}
