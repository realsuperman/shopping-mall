package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.exception.PageNotFoundException;
import com.bit.shoppingmall.global.LabelFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String isFirstRequest = request.getParameter("isSecond");
        String fileName;

        if(page == null || page.equals("add")){ // 초기 화면이거나 nav에서 선택한 경우
            fileName = "admin";
        }else if(page.equals("stock") || page.equals("stat")){
            request.setAttribute("page",page);
            fileName = "stockAndStat";
        }else{ // 해당하는 어드민 페이지가 없는 경우
            throw new PageNotFoundException();
        }

        if(isFirstRequest == null){ // page.jsp 요청
            StringBuilder redirectUrl = new StringBuilder();
            redirectUrl.append("/admin");
            if(page!=null){
                redirectUrl.append("?page=");
                redirectUrl.append(page);
            }

            request.setAttribute("redirectUrl", redirectUrl.toString());
            request.setAttribute("page",page);
            RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+"page"+LabelFormat.SUFFIX.label());
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}