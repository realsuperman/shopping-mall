package com.bit.shoppingmall.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderSetDto {
    private Long orderSetId;
    private String orderCode;
    private String orderAddress;
    private String orderPhoneNumber;
    private long distinctItemCount;
}
