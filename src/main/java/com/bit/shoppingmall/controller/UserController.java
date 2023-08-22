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

public class UserController extends HttpServlet {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "userLoginRegister" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));

        try {
            if (path.equals("/user/login")) {
                login(request, response);
            } else if (path.equals("/user/sign-up")) {
                signUp(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // post login
    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("로그인");
        LoginRequest loginRequest = new LoginRequest(request.getParameter("email"), request.getParameter("password"));
        LoginResponse loginResponse = userService.login(loginRequest);

        request.getSession().setAttribute("login_user", loginResponse.getLoginUser());
        request.getSession().setAttribute("grade", loginResponse.getGrade());
        request.getSession().setAttribute("discount_rate", loginResponse.getDiscountRate());

        System.out.println(loginResponse);
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

    // post sign-up
    private void signUp(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String address = request.getParameter("address") + request.getParameter("address_detail");
        SignUpRequest signUpRequest = new SignUpRequest(request.getParameter("email"), request.getParameter("password"), request.getParameter("username"), request.getParameter("phone_number"), address);

        userService.signUp(signUpRequest);
        response.sendRedirect("home");
    }

}
