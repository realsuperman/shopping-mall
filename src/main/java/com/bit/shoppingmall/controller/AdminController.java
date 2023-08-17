package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.service.AdminService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cargo/list")
public class AdminController extends HttpServlet {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("result", adminService.selectAll());
    }
}

/*
    조회, 검색, 업데이트

    url <> get, post , put ,patch

 */