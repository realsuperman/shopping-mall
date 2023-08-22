package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.dto.OrderSetDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderSetDao {
    public List<OrderSetDto> getConsumerOrderSetDtoList(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectList("order.getConsumerOrderSetList", consumerId);
    }
}
