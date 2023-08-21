package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.dto.OrderSetDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderSetServiceTest {

    private OrderSetService orderSetService;

    private SqlSession sqlSession;

    @BeforeEach
    void before() {
        orderSetService = new OrderSetService(new OrderSetDao());
        sqlSession = GetSessionFactory.getInstance().openSession();
    }

    @AfterEach
    void after() {
        sqlSession.close();
    }

    @Test
    void getConsumerOrderSetListTest() {
        List<OrderSetDto> orderSetDtoList = orderSetService.getConsumerOrderSetList(sqlSession, 1L);

        Assertions.assertEquals(1L, orderSetDtoList.size());
        Assertions.assertEquals(2, orderSetDtoList.get(0).getDistinctItemCount());
    }
}
