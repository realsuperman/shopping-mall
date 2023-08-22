package com.bit.shoppingmall.domain;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Getter
public class CartItem {
    private Long itemId;
    private Long itemQuantity;
    private Long cartId;
    private Long consumerId;

    public void increaseQuantity(Long itemQuantity) {
        this.itemQuantity += itemQuantity;
    }
}
