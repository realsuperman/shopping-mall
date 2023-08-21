package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.OrderDetailService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderDetailController extends HttpServlet {

    private final OrderDetailService orderDetailService;

    private final String fileName = "orderDetail";

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO : Dummy. REMOVE
        Long orderSetId = 1L;
        request.setAttribute("orderInfo", orderDetailService.getOrderInfo(orderSetId));

        List<OrderDetailDto> orderDetailList = orderDetailService.getOrderDetailList(orderSetId);
        request.setAttribute("orderDetailList", orderDetailList);

        request.setAttribute("orderSetTotalBuyPrice", orderDetailService.getOrderSetTotalBuyPrice(orderDetailList));

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
