package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.dto.OrderSetDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderSetService {
    private final OrderSetDao orderSetDao;

    private final SqlSession sqlSession;

    public OrderSetService(OrderSetDao orderSetDao) {
        this.orderSetDao = orderSetDao;
        this.sqlSession = GetSessionFactory.getInstance().openSession(true);
    }

    // TODO : parameter로 Map 생성해서 Dao로
    public List<OrderSetDto> getConsumerOrderSetList(Long consumerId) {
        return orderSetDao.getConsumerOrderSetDtoList(this.sqlSession, consumerId);
    }
}
