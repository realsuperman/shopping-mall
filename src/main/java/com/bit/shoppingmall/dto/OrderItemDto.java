package com.bit.shoppingmall.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {
    private Long itemId;
    private Long cartId;
    private long itemQuantity;
    private long itemPrice;
}
