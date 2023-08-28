package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.exception.MessageException;
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

        Long orderSetId = Long.valueOf(request.getParameter("orderSetId"));
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");

        // TODO : Use Validation
        if(orderDetailService.getConsumerId(orderSetId) != consumer.getConsumerId()) {
            response.sendRedirect("/");
            throw new MessageException("유효한 사용자가 아닙니다");
        }

        request.setAttribute("orderSetId", orderSetId);
        request.setAttribute("orderInfo", orderDetailService.getOrderInfo(orderSetId));

        List<OrderDetailDto> orderDetailList = orderDetailService.getOrderDetailList(orderSetId);
        request.setAttribute("orderDetailList", orderDetailList);

        request.setAttribute("orderSetTotalBuyPrice", orderDetailService.getOrderSetTotalBuyPrice(orderDetailList));

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
