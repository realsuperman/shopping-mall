package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderDetailDao {
    public List<OrderAddressInfoDto> getOrderAddressInfo(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectList("order.getOrderAddressInfo", orderSetId);
    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectList("order.getOrderDetailList", orderSetId);
    }
}
