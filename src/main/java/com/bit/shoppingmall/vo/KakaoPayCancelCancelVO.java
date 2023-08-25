package com.bit.shoppingmall.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoPayCancelCancelVO {
    private String cid;
    private String tid;
    private Integer cancelAmount;
    private Integer cancelTaxFreeAmount;
}
