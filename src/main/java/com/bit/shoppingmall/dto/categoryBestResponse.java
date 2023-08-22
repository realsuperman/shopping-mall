package com.bit.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
public class categoryBestResponse {
    private long itemId;
    private long categoryId;
    private String itemName;
    private long itemPrice;
    private String itemDescription;
    private String itemImagePath;
    private Timestamp itemRegisterTime;
    private long cnt;
}
