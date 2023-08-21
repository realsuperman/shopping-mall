package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.OrderSetService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderSetController extends HttpServlet {

    private final OrderSetService orderSetService;

    private final String fileName = "orderSetList";

    public OrderSetController(OrderSetService orderSetService) {
        this.orderSetService = orderSetService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Long consumerId = request.getSession().getAttribute("loginUser").getConsumerId();
        // TODO : Dummy. REMOVE
        Long consumerId = 1L;
        request.setAttribute("consumerOrderSetList", orderSetService.getConsumerOrderSetList(consumerId));

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
