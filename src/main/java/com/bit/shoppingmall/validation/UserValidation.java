package com.bit.shoppingmall.validation;

import com.bit.shoppingmall.global.Validation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 회원가입 post, 사용자 정보 수정(비밀번호), 사용자 정보 수정(폰번호, 주소) 확인
public class UserValidation extends HttpServlet {

    Validation validation = new Validation();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String uri = request.getRequestURI();
        String path = uri.substring(0, uri.lastIndexOf("."));

        if (path.equals("/user-validation/sign-up")) {
            signUpFormValidation(request, response);
        } else if (path.equals("/user-validation/my-page-info/pass")) {
            passUpdateFormValidation(request, response);
        }
    }

    public void signUpFormValidation(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userName = request.getParameter("username");

        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address") + request.getParameter("address_detail");

        validation.validateString("이메일", email, 127);
        validation.validateString("비밀번호", password, 127);
        validation.validateString("유저이름", userName, 127);
        validation.validateString("휴대폰 번호", phoneNumber, 127);
        validation.validateString("주소", address, 127);

        validation.validateEmail(email);
        validation.validatePassword(password);

    }

    public void passUpdateFormValidation(HttpServletRequest request, HttpServletResponse response) {

        String password = request.getParameter("update_password");
        validation.validateString("변경할 비밀번호", password, 127);
        validation.validatePassword(password);
    }
}
