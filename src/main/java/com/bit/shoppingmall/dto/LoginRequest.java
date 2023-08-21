package com.bit.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest {

    private String userEmail;
    private String password;

}
