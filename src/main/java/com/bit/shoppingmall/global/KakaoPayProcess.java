package com.bit.shoppingmall.global;

import com.bit.shoppingmall.vo.KakaoPayCancelCancelVO;
import com.bit.shoppingmall.vo.KakaoPayVO;

import java.io.IOException;

public class KakaoPayProcess {
    public static int process(KakaoPayVO kakaoPayVO) throws IOException {
        String payloadData = "cid=" + kakaoPayVO.getCid()
                + "&tid=" + kakaoPayVO.getTid()
                + "&partner_order_id=" + kakaoPayVO.getPartnerOrderId()
                + "&partner_user_id=" + kakaoPayVO.getPartnerUserId()
                + "&pg_token=" + kakaoPayVO.getPgToken();
        return KakaoPayCommonProcess.getKakaoConnection("https://kapi.kakao.com/v1/payment/approve", payloadData)
                .getResponseCode();
    }

    public static int cancel(KakaoPayCancelCancelVO kakaoPayCancelCancelVO) throws IOException {
        String payloadData = "cid=" + kakaoPayCancelCancelVO.getCid()
                + "&tid=" + kakaoPayCancelCancelVO.getTid()
                + "&cancel_amount=" + kakaoPayCancelCancelVO.getCancelAmount()
                + "&cancel_tax_free_amount=" + kakaoPayCancelCancelVO.getCancelTaxFreeAmount();
        return KakaoPayCommonProcess.getKakaoConnection("https://kapi.kakao.com/v1/payment/cancel", payloadData)
                .getResponseCode();
    }
}