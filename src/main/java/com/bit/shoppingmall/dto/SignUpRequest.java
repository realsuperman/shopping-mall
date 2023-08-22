package com.bit.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SignUpRequest {

    private String userEmail;
    private String password;
    private String userName;
    private String phoneNumber;
    private String address;

}
