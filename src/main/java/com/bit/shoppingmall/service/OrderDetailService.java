package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    SqlSession sqlSession;

    public OrderDetailService(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    /**
     * 특정 order_set_id에 해당하는 배송지 주소와 배송지 연락처를 dto 형태로 반환
     * @param sqlSession
     * @param orderSetId
     * @return OrderAddressInfoDto
     */
    public OrderAddressInfoDto getOrderAddressInfo(SqlSession sqlSession, Long orderSetId) {
        return orderDetailDao.getOrderAddressInfo(sqlSession, orderSetId);
    }

    /**
     * 특정 order_set_id에 해당하는 order_detail들의 상품명, 갯수, 구매 당시 가격, 상태 이름을 dto형태로 List에 담아 반환
     * @param sqlSession
     * @param orderSetId
     * @return List<OrderDetailDto>
     */
    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return orderDetailDao.getOrderDetailList(sqlSession, orderSetId);
    }

    /**
     * 총 결제 금액을 반환
     * @param orderDetailDtoList
     * @return 총 결제 금액
     */
    public long getTotalBuyPrice(List<OrderDetailDto> orderDetailDtoList, String status) {
        long result = 0L;
        for(OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if(orderDetailDto.getStatus().equals(status)) {
                result += orderDetailDto.getBuyPrice() * orderDetailDto.getItemQuantity();
            }
        }
        return result;
    }
}
