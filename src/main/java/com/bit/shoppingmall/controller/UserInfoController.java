package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
import com.bit.shoppingmall.dto.SignUpRequest;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserInfoController extends HttpServlet {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
//        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "" + LabelFormat.SUFFIX.label());
//        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


    // post user
    // 사용자 정보 수정
    private void UserInfo(HttpServletRequest request, HttpServletResponse response) {


    }

}
