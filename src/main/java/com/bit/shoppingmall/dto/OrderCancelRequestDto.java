package com.bit.shoppingmall.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class OrderCancelRequestDto {
    Long orderSetId;
    List<OrderCancelDto> orderCancelDtoList;
}
