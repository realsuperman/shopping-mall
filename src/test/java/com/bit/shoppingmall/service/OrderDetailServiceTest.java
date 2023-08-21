package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class OrderDetailServiceTest {

    private OrderDetailService orderDetailService;
    private SqlSession sqlSession;

    private final Logger logger = Logger.getLogger("OrderDetailServiceTest");

    @BeforeEach
    void before() {
        orderDetailService = new OrderDetailService(new OrderDetailDao());
        sqlSession = GetSessionFactory.getInstance().openSession();
    }

    @AfterEach
    void after() {
        sqlSession.close();
    }

    @Test
    void getOrderAddressInfoTest() {
        OrderAddressInfoDto orderAddressInfoDto = orderDetailService.getOrderAddressInfo(sqlSession, 1L);

        Assertions.assertEquals("서울특별시 서초구 KR 1319-13 현대타워아파트 103호", orderAddressInfoDto.getOrderAddress());
        Assertions.assertEquals("01054685177", orderAddressInfoDto.getOrderPhoneNumber());
    }

    @Test
    void getOrderDetailList() {
        List<OrderDetailDto> orderDetailDtoList = orderDetailService.getOrderDetailList(sqlSession, 1L);

        Assertions.assertEquals(2, orderDetailDtoList.size());
    }

    @Test
    void getTotalBuyPriceTest() {
        List<OrderDetailDto> orderDetailDtoList = orderDetailService.getOrderDetailList(sqlSession, 1L);
        long totalBuyPrice = orderDetailService.getTotalBuyPrice(orderDetailDtoList, "결제 완료");

        Assertions.assertEquals((Long) 2502L, (Long) totalBuyPrice);
    }
}
