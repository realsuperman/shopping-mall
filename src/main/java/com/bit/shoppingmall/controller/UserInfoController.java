package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.UpdatePasswordRequest;
import com.bit.shoppingmall.dto.UpdateUserRequest;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class UserInfoController extends HttpServlet {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        if (path.equals("/my-page-info")) {
            rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPageUpdate" + LabelFormat.SUFFIX.label());
        }
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));

        try {
            if (path.equals("/my-page-info")) {
                userInfo(request, response);
            } else if (path.equals("/my-page-info/pass")) {
                userPassword(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // /my-page-info/pass post
    // 사용자 정보 수정 - 비밀번호
    private void userPassword(HttpServletRequest request, HttpServletResponse response) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ServletException {

        try {
            Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(consumer.getUserEmail(), request.getParameter("original_password"), request.getParameter("update_password"));

            userService.updatePassword(updatePasswordRequest);
            Consumer updatedConsumer = userService.readUserOne(updatePasswordRequest.getUserEmail());

            request.getSession().setAttribute("login_user", updatedConsumer);

            RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
            rd.forward(request, response);
        } catch (MessageException e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPageUpdate" + LabelFormat.SUFFIX.label());
            dispatcher.forward(request, response);
        }
    }

    // /my-page-info post
    // 사용자 정보 수정 - 폰번호 또는 주소
    private void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Consumer sessionConsumer = (Consumer) request.getSession().getAttribute("login_user");
        String email = sessionConsumer.getUserEmail();
        String updatePhoneNumber = request.getParameter("phone_number");
        String updateAddress = request.getParameter("address") + " " + request.getParameter("address_detail");

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(email);

        if (!updatePhoneNumber.equals(sessionConsumer.getPhoneNumber())) {
            updateUserRequest.setUpdatePhoneNumber(updatePhoneNumber);
        }
        if (!updateAddress.equals(sessionConsumer.getAddress())) {
            updateUserRequest.setUpdateAddress(updateAddress);
        }

        userService.updateUserInfo(updateUserRequest);
        Consumer consumer = userService.readUserOne(email);
        request.getSession().setAttribute("login_user", consumer);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "myPage" + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
