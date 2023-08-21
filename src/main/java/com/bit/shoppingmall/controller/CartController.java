package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.exception.NotContainedAnything;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CartService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart/lists")
public class CartController extends HttpServlet {
    private final CartService cartService;

    private final String fileName = "cart";

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long loginedId = 1L;//하드코딩
        try {
            List<CartItem> cartItems = cartService.get(loginedId);
            request.setAttribute("cartItems", cartItems);
        } catch (NotContainedAnything e) {
            //에러 처리
        }

        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}