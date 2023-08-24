package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.cj.log.Log;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
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
//        // TODO : request에서 가져온 총 결제 금액과 할인률을 적용한 구매 상품 목록의 실제 결제 금액이 같은지 검증
        String requestData = request.getParameter("orderItemDtoList");
        request.getSession().setAttribute("orderItemDtoList", requestData);

        try {
            response.sendRedirect("/order");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = (String) request.getSession().getAttribute("orderItemDtoList");
        request.getSession().removeAttribute("orderItemDtoList");

        ObjectMapper mapper = new ObjectMapper();
        List<OrderItemDto> orderItemDtoList = mapper.readValue(requestData, new TypeReference<List<OrderItemDto>>() {});
        logger.info(orderItemDtoList.toString());

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    // order 취소 페이지 요청
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 실 취소는 PaymentController
    }
}
