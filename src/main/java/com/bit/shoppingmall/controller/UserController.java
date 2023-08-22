package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
import com.bit.shoppingmall.dto.SignUpRequest;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.UserService;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    private final UserService userService;

//    private final String fileName = "";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println("userController - get "+ url);
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "userLoginRegister" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("userController - post");
        String email = request.getParameter("email");
        String url = request.getRequestURI();
        System.out.println(email);
        System.out.println(url);

//        response.setCharacterEncoding("UTF-8");
//        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
//        rd.forward(request, response);

    }

    // post login
    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String data = request.getParameter("loginData");
        LoginRequest loginRequest = (LoginRequest) (new Gson().fromJson(data, LoginRequest.class));

        LoginResponse loginResponse = userService.login(loginRequest);
        request.setAttribute("login_user", loginResponse.getLoginUser());
        request.setAttribute("grade", loginResponse.getGrade());
        request.setAttribute("discount_rate", loginResponse.getDiscountRate());

//        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "mainLogin" + LabelFormat.SUFFIX.label());
//        rd.forward(request, response);

    }

    // post sing-up
    // 객체로 받을까 그냥 para로 받을까?
    private void signUp(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String data = request.getParameter("sighUpData");
        SignUpRequest signUpRequest = (SignUpRequest) (new Gson().fromJson(data, SignUpRequest.class));

        if (userService.signUp(signUpRequest) == 0) {
            throw new Exception("로그인 에러"); // 에러 시, 페이지?
        }

//        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "mainNotLogin" + LabelFormat.SUFFIX.label());
//        rd.forward(request, response);

    }

    // post user
    // 사용자 정보 수정
    private void UserInfo(HttpServletRequest request, HttpServletResponse response) {


    }

}
