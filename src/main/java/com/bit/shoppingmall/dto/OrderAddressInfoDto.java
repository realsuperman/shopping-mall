package com.bit.shoppingmall.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderAddressInfoDto {
    private String orderAddress;
    private String orderPhoneNumber;
}
