package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaymentController extends HttpServlet {

    private final OrderService orderService;
    
    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    // 주문 취소
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
