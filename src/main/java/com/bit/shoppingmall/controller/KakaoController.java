package com.bit.shoppingmall.controller;


import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 내일 JJM한테 설명 예정(트랜잭션 및 카카오 결제 API 구조도)
 * 내가 이겼따 카카오 결제 API
 * ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
 */

/**
 * 1. 호출시 특정 JS에서 버튼을 눌러서 정보를 보낸다
 * 2. 리다이렉션 주소가 날라와서 그걸로 리다이렉션 친다
 * 3. 결재 성공이 떨어지면 그 주소로 요청을 한다
 */
public class KakaoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "KakaoAK 1c2665d0fbd94a38d95ac129fa3d165a");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);

        String cid = "TC0ONETIME"; // 가맹점 코드[FIX]
        String partnerOrderId = "csh11"; // 주문번호
        String partnerUserId = "csh"; // 회원번호
        String itemName = "아이헤이트프론트"; // 상품명
        int quantity = 1; // 수량
        int totalAmount = 10000; // 총금액
        int taxFreeAmount = 0; // 비과세
        String approvalUrl = "http://localhost/kakao/success?mode=csh"; // 결제 성공시 어디로 보낼래?(여기다가 필요한 정보들 넣자)
        String cancelUrl = "http://localhost//kakao/fail"; // 결재 취소시 어디로 보낼래?
        String failUrl = "http://localhost/kakao/fail"; // 결제 실패시 어디로 보낼래?

        String payloadData = "cid=" + cid
                + "&partner_order_id=" + partnerOrderId
                + "&partner_user_id=" + partnerUserId
                + "&item_name=" + itemName
                + "&quantity=" + quantity
                + "&total_amount=" + totalAmount
                + "&tax_free_amount=" + taxFreeAmount
                + "&approval_url=" + approvalUrl
                + "&cancel_url=" + cancelUrl
                + "&fail_url=" + failUrl;

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(payloadData);

        if(connection.getResponseCode() == HttpStatus.SC_OK){ // 성공
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder rep = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                rep.append(inputLine);
            }
            in.close();

            String jsonResponse = rep.toString();// 받은 응답을 JSON 문자열로 변환하여 할당
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                try (PrintWriter writer = response.getWriter()) {
                    writer.write(jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException("결제 모듈을 불러낼때 실패했다."); // TODO 메시지 날리는 예외 공통처리
        }
    }
}