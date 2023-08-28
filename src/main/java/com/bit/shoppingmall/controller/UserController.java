package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
import com.bit.shoppingmall.dto.SignUpRequest;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class UserController extends HttpServlet {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));

        try {
            if (path.equals("/user")) {
                response.setCharacterEncoding("UTF-8");
                RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "userLoginRegister" + LabelFormat.SUFFIX.label());
                rd.forward(request, response);
            } else if (path.equals("/logout")) {
                logout(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));

        try {
            if (path.equals("/user")) {
                login(request, response);
            } else if (path.equals("/user/sign-up")) {
                signUp(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // login post
    private void login(HttpServletRequest request, HttpServletResponse response) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ServletException, IOException {

        try {
            LoginRequest loginRequest = new LoginRequest(request.getParameter("email"), request.getParameter("password"));
            LoginResponse loginResponse = userService.login(loginRequest);
            request.getSession().setAttribute("login_user", loginResponse.getLoginUser());

            if (loginResponse.getLoginUser().getIsAdmin() == 0) {
                request.getSession().setAttribute("grade", loginResponse.getGrade());
                request.getSession().setAttribute("discount_rate", loginResponse.getDiscountRate());
            }
            response.sendRedirect("/");
        } catch (MessageException e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "userLoginRegister" + LabelFormat.SUFFIX.label());
            rd.forward(request, response);        }
    }

    // sign-up post
    private void signUp(HttpServletRequest request, HttpServletResponse response) throws InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, ServletException {

        try {
            String address = request.getParameter("address") + request.getParameter("address_detail");
            SignUpRequest signUpRequest = new SignUpRequest(request.getParameter("email"), request.getParameter("password"), request.getParameter("username"), request.getParameter("phone_number"), address);

            userService.signUp(signUpRequest);
            response.sendRedirect("/");
        } catch (MessageException e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + "userLoginRegister" + LabelFormat.SUFFIX.label());
            rd.forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        response.sendRedirect("/");
    }
}
