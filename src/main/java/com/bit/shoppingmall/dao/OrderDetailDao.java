package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@NoArgsConstructor
public class OrderDetailDao {
    public OrderAddressInfoDto getOrderAddressInfo(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectOne("order.getOrderAddressInfo", orderSetId);
    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectList("order.getOrderDetailList", orderSetId);
    }

    public Long getConsumerTotalBuyPrice(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectOne("order.getConsumerTotalBuyPrice", consumerId);
    }
}
