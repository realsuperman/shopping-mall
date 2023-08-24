package com.bit.shoppingmall.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoPayVO {
    private String cid;
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private String pgToken;
}