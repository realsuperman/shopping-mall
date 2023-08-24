package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.dto.OrderSetDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderSetService {
    private final OrderSetDao orderSetDao;

    public OrderSetService(OrderSetDao orderSetDao) {
        this.orderSetDao = orderSetDao;
    }

    // TODO : parameter로 Map 생성해서 Dao로
    public List<OrderSetDto> getConsumerOrderSetList(Long consumerId) {
        List<OrderSetDto> consumerOrderSetDtoList;
        try(SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            consumerOrderSetDtoList = orderSetDao.getConsumerOrderSetDtoList(sqlSession, consumerId);
        }
        return consumerOrderSetDtoList;
    }
}
