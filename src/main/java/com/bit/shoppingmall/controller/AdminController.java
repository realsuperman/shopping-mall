package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.exception.PageNotFoundException;
import com.bit.shoppingmall.global.LabelFormat;
import org.apache.ibatis.javassist.NotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminController extends HttpServlet {
    //private final String fileName = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String fileName;

        if(page == null || page.equals("add")){ // 초기 화면이거나 nav에서 선택한 경우
            fileName = "admin";
        }else if(page.equals("stock") || page.equals("stat")){
            request.setAttribute("page",page);
            fileName = "stockAndStat";
        }else{ // 해당하는 어드민 페이지가 없는 경우
            throw new PageNotFoundException();
        }

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}