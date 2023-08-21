package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@NoArgsConstructor
public class OrderDetailDao {
    public OrderInfoDto getOrderInfo(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectOne("order.getOrderInfo", orderSetId);
    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectList("order.getOrderDetailList", orderSetId);
    }

    public long getConsumerTotalBuyPrice(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectOne("order.getConsumerTotalBuyPrice", consumerId);
    }
}
