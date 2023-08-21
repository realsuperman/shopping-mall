package com.bit.shoppingmall.dto;

import com.bit.shoppingmall.domain.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private Consumer userInfo;
    private String grade;
    private double discountRate;

}
