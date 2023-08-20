package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.StatusDao;
import com.bit.shoppingmall.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// 트랜잭션 테스트가 필요한 경우에만 RootTest를 extends
public class StatusServiceTest {
    private StatusService statusService;

    @BeforeEach
    public void before(){
        statusService = new StatusService(new StatusDao());
    }

    // select의 경우 예외만 발생안하면 테스트 통과로 가정
    @Test
    void selectAll() {
        List<Status> statuses = statusService.selectAll();
    }
}
