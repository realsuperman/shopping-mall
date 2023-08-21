package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.exception.RedirectionException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ItemController extends HttpServlet {
    private final String fileName = "admin";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        if(1==1){
            throw new IllegalArgumentException("유효하지 않은 입력 데이터입니다.");
        }
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            throw new RedirectionException();
        }

    }
}
