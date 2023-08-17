package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cargo/list")
public class AdminController extends HttpServlet {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", adminService.selectAll());
        RequestDispatcher rd = request.getRequestDispatcher("cargo.jsp");
        rd.forward(request, response);
    }
}

/*
    조회, 검색, 업데이트

    url <> get, post , put ,patch

 */