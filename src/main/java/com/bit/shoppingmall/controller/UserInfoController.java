package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.*;
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

//        response.setCharacterEncoding("UTF-8");
//        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "" + LabelFormat.SUFFIX.label());
//        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


    // post
    // 사용자 정보 수정 - 비밀번호
    private void UserPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(request.getParameter("email"), request.getParameter("originalPassword"), request.getParameter("updatePassword"));
        userService.updatePassword(updatePasswordRequest);

        Consumer consumer = userService.readUserOne(updatePasswordRequest.getUserEamil());
        request.getSession().setAttribute("login_user", consumer);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

    // post
    // 사용자 정보 수정 - 폰번호
    private void UserPhoneNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(request.getParameter("email"), request.getParameter("phone_number"));
        userService.updatePhoneNumber(updateUserRequest);

        Consumer consumer = userService.readUserOne(updateUserRequest.getUserEamil());
        request.getSession().setAttribute("login_user", consumer);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

    // post
    // 사용자 정보 수정 - 주소
    private void UserAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(request.getParameter("email"), request.getParameter("phone_number"));
        userService.updateAddress(updateUserRequest);

        Consumer consumer = userService.readUserOne(updateUserRequest.getUserEamil());
        request.getSession().setAttribute("login_user", consumer);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);

    }

}
