package com.bit.shoppingmall.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDetail {
    private Long orderDetailId;
    private Long orderSetId;
    private Long cargoId;
    private Long statusId;
    private long buyPrice;
}
