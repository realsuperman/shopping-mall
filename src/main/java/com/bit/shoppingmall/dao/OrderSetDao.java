package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.dto.OrderSetDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderSetDao {
    // TODO : Map<String, Long> type parameter pagination
    public List<OrderSetDto> getConsumerOrderSetDtoList(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectList("order.getConsumerOrderSetList", consumerId);
    }
}
