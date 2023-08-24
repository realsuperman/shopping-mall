package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.OrderService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.cj.log.Log;
import lombok.NoArgsConstructor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@NoArgsConstructor
public class OrderController extends HttpServlet {

    private final String fileName = "order";

    private final Logger logger = Logger.getLogger("Order Controller");

    // order 페이지 요청
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("post /order");

        // 실 주문은 PaymentController
//        String jsonBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

//        logger.info(jsonBody);

        // TODO : request.setAttribute(OrderInfoDto)
//        request.setAttribute("orderItemDtoList", new Gson().fromJson(jsonBody, new TypeToken<List<OrderItemDto>>(){}.getType()));


        // TODO : request에서 가져온 총 결제 금액과 할인률을 적용한 구매 상품 목록의 실제 결제 금액이 같은지 검증

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    // order 취소 페이지 요청
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 실 취소는 PaymentController
    }
}
