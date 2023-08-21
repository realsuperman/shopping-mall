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

    public List<OrderAddressInfoDto> getOrderAddressInfo(SqlSession sqlSession) {

    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession) {

    }
}
