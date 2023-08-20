package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminController extends HttpServlet {
    private final AdminService adminService;
    private final String fileName = "admin";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", adminService.selectAll());

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}