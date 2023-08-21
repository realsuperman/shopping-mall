package com.bit.shoppingmall.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class Item {
    private Long itemId;
    private Long categoryId;
    private String itemName;
    private Long itemPrice;
    private String itemDescription;
    private String itemImagePath;
    private Timestamp itemRegisterTime; // java.sql.Timestamp가 아닌 LocalDateTime타입으로 바꾸고 싶음

}
