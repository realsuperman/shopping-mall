package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    SqlSession sqlSession;

    public OrderDetailService(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    public OrderAddressInfoDto getOrderAddressInfo(SqlSession sqlSession, Long orderSetId) {
        return orderDetailDao.getOrderAddressInfo(sqlSession, orderSetId);
    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return orderDetailDao.getOrderDetailList(sqlSession, orderSetId);
    }
}
