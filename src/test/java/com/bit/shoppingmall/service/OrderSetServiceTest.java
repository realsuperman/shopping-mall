package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.dto.OrderSetDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderSetServiceTest {

    private OrderSetService orderSetService;

    @BeforeEach
    void before() {
        orderSetService = new OrderSetService(new OrderSetDao());
    }

    @AfterEach
    void after() {
    }

    @Test
    void getConsumerOrderSetListTest() {
        List<OrderSetDto> orderSetDtoList = orderSetService.getConsumerOrderSetList(1L);

        Assertions.assertEquals(9L, orderSetDtoList.size());
//        Assertions.assertEquals(2, orderSetDtoList.get(0).getDistinctItemCount());
    }
}
