package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.OrderSet;
import com.bit.shoppingmall.dto.OrderSetDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderSetDao {
    // TODO : Map<String, Long> type parameter pagination
    public List<OrderSetDto> getConsumerOrderSetDtoList(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectList("order.getConsumerOrderSetList", consumerId);
    }

    // TODO : Fail 시 return -1
    public Long insertOrderSet(SqlSession sqlSession, OrderSet orderSet) {
        if(sqlSession.insert("order.insertOrderSet", orderSet) > 0) {
            return orderSet.getOrderSetId();
        }
        return -1L;
    }

    public OrderSet selectByOrderSetId(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectOne("order.selectByOrderSetId", orderSetId);
    }
}
