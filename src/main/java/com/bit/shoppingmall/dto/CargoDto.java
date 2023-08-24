package com.bit.shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CargoDto {
    private Long cargoId;
    private String itemName;
    private Long statusId;
}
