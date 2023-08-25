package com.bit.shoppingmall.global;

import com.bit.shoppingmall.vo.KakaoPayCancelCancelVO;
import com.bit.shoppingmall.vo.KakaoPayVO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class KakaoPayProcess {
    public int process(KakaoPayVO kakaoPayVO) throws IOException {
        URL url = new URL("https://kapi.kakao.com/v1/payment/approve");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "KakaoAK 1c2665d0fbd94a38d95ac129fa3d165a");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);

        String payloadData = "cid=" + kakaoPayVO.getCid()
                + "&tid=" + kakaoPayVO.getTid()
                + "&partner_order_id=" + kakaoPayVO.getPartnerOrderId()
                + "&partner_user_id=" + kakaoPayVO.getPartnerUserId()
                + "&pg_token=" + kakaoPayVO.getPgToken();

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(payloadData);

        return connection.getResponseCode();
    }

    public int cancel(KakaoPayCancelCancelVO kakaoPayCancelCancelVO) throws IOException {
        URL url = new URL("https://kapi.kakao.com/v1/payment/approve");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "KakaoAK 1c2665d0fbd94a38d95ac129fa3d165a");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);

        String payloadData = "cid=" + kakaoPayCancelCancelVO.getCid()
                + "&tid=" + kakaoPayCancelCancelVO.getTid()
                + "&cancel_amount=" + kakaoPayCancelCancelVO.getCancelAmount()
                + "&cancel_tax_free_amount=" + kakaoPayCancelCancelVO.getCancelTaxFreeAmount();

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(payloadData);

        return connection.getResponseCode();
    }
}