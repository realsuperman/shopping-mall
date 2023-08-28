package com.bit.shoppingmall.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class OrderCancelDto {
    private Long itemId;
    private String itemName;
    private long itemQuantity;
    private long buyPrice;
    private String statusName;
}
