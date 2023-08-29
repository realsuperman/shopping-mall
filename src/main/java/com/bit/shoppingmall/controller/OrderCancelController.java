package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.OrderCancelDto;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.OrderDetailService;
import com.bit.shoppingmall.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class OrderCancelController extends HttpServlet {

    private final String fileName = "orderCancel";

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    public OrderCancelController(OrderDetailService orderDetailService, OrderService orderService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    private final Logger logger = Logger.getLogger("order cancel controller");

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
        request.setAttribute("orderCancelDtoList", orderDetailService.getOrdersToCancel(orderSetId));

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*logger.info("orderSetId: "+request.getParameter("orderSetId"));
        Long orderSetId = Long.parseLong(request.getParameter("orderSetId"));
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");

        // TODO : Use Validation
        if(orderDetailService.getConsumerId(orderSetId) != consumer.getConsumerId()) {
            response.sendRedirect("/");
            throw new MessageException("유효한 사용자가 아닙니다");
        }*/

        // orderSetId, List<OrderCancelDto> list
        ObjectMapper mapper = new ObjectMapper();
        String requestData = request.getParameter("orderCancelDtoList");
        List<OrderCancelDto> orderCancelDtoList = mapper.readValue(requestData, new TypeReference<List<OrderCancelDto>>() {});
        request.setAttribute("orderCancelDtoList", orderCancelDtoList);

//        orderService.cancelOrder(orderSetId, orderCancelDtoList);
        response.sendRedirect("/");
    }
}
