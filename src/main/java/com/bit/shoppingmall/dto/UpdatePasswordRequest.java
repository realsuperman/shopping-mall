package com.bit.shoppingmall.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdatePasswordRequest {
    private String userEamil;
    private String originalPassword;
    private String updatePassword;
}
