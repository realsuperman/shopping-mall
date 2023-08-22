package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    public OrderDetailService(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    /**
     * 특정 order_set_id에 해당하는 배송지 주소와 배송지 연락처를 dto 형태로 반환
     * @param orderSetId
     * @return OrderAddressInfoDto
     */
    // order_detail쪽에서 order_set의 정보가 필요해서 만든 메소드라 위치가 애매
    public OrderInfoDto getOrderInfo(Long orderSetId) {
        OrderInfoDto orderInfoDto;
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            orderInfoDto = orderDetailDao.getOrderInfo(sqlSession, orderSetId);
        }
        return orderInfoDto;
    }

    /**
     * 특정 order_set_id에 해당하는 order_detail들의 상품명, 갯수, 구매 당시 가격, 상태 이름을 dto형태로 List에 담아 반환
     * @param orderSetId
     * @return List<OrderDetailDto>
     */
    public List<OrderDetailDto> getOrderDetailList(Long orderSetId) {
        List<OrderDetailDto> orderDetailDtoList;
        try(SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            orderDetailDtoList = orderDetailDao.getOrderDetailList(sqlSession, orderSetId);
        }
        return orderDetailDtoList;
    }

    /**
     * orderset의 총 결제 금액을 반환
     * @param orderDetailDtoList
     * @return 총 결제 금액
     */
    // TODO : 하드 코딩 수정
    public long getOrderSetTotalBuyPrice(List<OrderDetailDto> orderDetailDtoList) {
        long result = 0L;
        for(OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if(orderDetailDto.getStatusName().equals("취소") || orderDetailDto.getStatusName().equals("반품")) continue;
            result += orderDetailDto.getBuyPrice() * orderDetailDto.getItemQuantity();
        }
        return result;
    }

    public long getConsumerId(Long orderSetId) {
        long consumerId;
        try(SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            consumerId = orderDetailDao.getConsumerId(sqlSession, orderSetId);
        }
        return consumerId;
    }

    public long getConsumerTotalBuyPrice(Long consumerId) {
        long consumerTotalBuyPrice;
        try(SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            consumerTotalBuyPrice = orderDetailDao.getConsumerTotalBuyPrice(sqlSession, consumerId);
        }
        return consumerTotalBuyPrice;
    }
}
