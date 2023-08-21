package com.bit.shoppingmall.domain;

import com.google.type.DateTime;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderSet {
    private Long orderSetId;
    private Long consumerId;
    private String orderCode;
    private DateTime orderTime;
    private String orderAddress;
    private String orderPhoneNumber;
}
