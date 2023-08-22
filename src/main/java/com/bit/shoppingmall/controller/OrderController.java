package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderController extends HttpServlet {

    private final OrderService orderService;

    private final String fileName = "order";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // order 페이지 요청
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 실 주문은 PaymentController
    }

    // order 취소 페이지 요청
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 실 취소는 PaymentController
    }
}
