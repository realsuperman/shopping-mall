package com.bit.shoppingmall.dto;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Membership;
import lombok.*;

@Builder
@Getter
@ToString
public class LoginResponse {

    private Consumer loginUser;
    private String grade;
    private double discountRate;

    public LoginResponse(Consumer loginUser, String grade, double discountRate) {
        this.loginUser = loginUser;
        this.grade = grade;
        this.discountRate = discountRate;
    }

    public LoginResponse(Consumer loginUser) {
        this.loginUser = loginUser;
    }


}
