package com.bit.shoppingmall.domain;

import com.bit.shoppingmall.dto.SignUpRequest;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
public class Consumer {

    private Long consumerId;
    private String userEmail;
    private String password;
    private String phoneNumber;
    private String address;
    private String userName;
    private int isAdmin;

    public static Consumer signUpDtoToConsumer(SignUpRequest signUpDto) {
        return Consumer.builder().userEmail(signUpDto.getUserEmail()).password(signUpDto.getPassword()).phoneNumber(signUpDto.getPhoneNumber()).address(signUpDto.getAddress()).userName(signUpDto.getUserName()).isAdmin(0).build();
    }

}
