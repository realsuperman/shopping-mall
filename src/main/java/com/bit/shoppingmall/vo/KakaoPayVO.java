package com.bit.shoppingmall.vo;

import lombok.Builder;

@Builder
public class KakaoPayVO {
    private String tid; // 결제 고유 번호
    private String nextRedirectPcUrl; // PC 웹 리다이렉트 주소
    private String createdAt; // 결제 준비 요청 시간
}
