//package com.bit.shoppingmall.global;
//
//import lombok.Getter;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Map;
//
//@Getter
//public class TableCache<T> {
//    // redis
//    private Map<String, List<T>> cache;
//    private SimpleDateFormat lastmodifiedTime;// = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//    private boolean flag; // 동시성 고려를 위한 것임(모든 스레드는 읽기만 가능함)
//    // select * from users where id=?
//}