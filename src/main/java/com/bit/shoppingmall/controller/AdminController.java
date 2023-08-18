package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cargo/list")
public class AdminController extends HttpServlet {
    private final AdminService adminService;
    private final String fileName = "cargo";

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String method = (String) request.getAttribute("method");
        if(method.equals("GET")){
            doGet(httpServletRequest, httpServletResponse);
        }else if(method.equals("POST")){

        }else if(method.equals("PUT")){

        }else if(method.equals("DELETE")){

        }
    }

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