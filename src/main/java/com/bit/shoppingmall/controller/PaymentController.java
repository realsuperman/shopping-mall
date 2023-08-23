package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PaymentController extends HttpServlet {

    private final OrderService orderService;
    
    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long customerId = (Long) request.getSession().getAttribute("login_user");
        OrderInfoDto orderInfoDto = (OrderInfoDto) request.getAttribute("orderInfoDto");
        List<OrderItemDto> orderItemDtoList = (List<OrderItemDto>) request.getAttribute("orderItemDtoList");

        orderService.order(
                customerId,
                orderInfoDto,
                orderItemDtoList
        );

        response.sendRedirect("kakao pay url");
    }

    // 주문 취소
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
