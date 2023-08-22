package com.bit.shoppingmall.domain;

import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Item {
    private Long itemId;
    private Long categoryId;
    private String itemName;
    private Long itemPrice;
    private String itemDescription;
    private String itemImagePath;
    private Timestamp itemRegisterTime;

}
