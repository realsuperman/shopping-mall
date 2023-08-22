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

    public List<OrderSetDto> getConsumerOrderSetList(Long consumerId) {
        return orderSetDao.getConsumerOrderSetDtoList(GetSessionFactory.getInstance().openSession(), consumerId);
    }
}
