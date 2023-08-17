package com.bit.shoppingmall.global;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
public class TableCache<T> {
    private List<T> table;
    private SimpleDateFormat lastmodifiedTime;// = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private boolean flag; // 동시성 고려를 위한 것임(모든 스레드는 읽기만 가능함)

    // 저장시간,요청시간 비교해서 특정 시간 이상이거나 flag가 true라면 DB조회한다
    // 그런 메소드를 만드세요
}
