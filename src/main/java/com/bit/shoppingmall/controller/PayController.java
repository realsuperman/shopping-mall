package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.global.LabelFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayController extends HttpServlet {
    private final String fileName = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.getParameter("pg_token");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+"success"+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
