package com.bit.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class UpdateUserRequest {

    private String userEmail;
    private String updateData;

}
