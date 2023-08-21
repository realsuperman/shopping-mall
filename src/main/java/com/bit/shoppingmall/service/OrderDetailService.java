package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dto.OrderAddressInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.global.GetSessionFactory;

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
    // order_set table select할 때 시간도 select해서 order_detail 조회할 때 페이지에 넘겨주기만 하는게 나을 듯?
    public OrderAddressInfoDto getOrderAddressInfo(Long orderSetId) {
        return orderDetailDao.getOrderAddressInfo(GetSessionFactory.getInstance().openSession(), orderSetId);
    }

    /**
     * 특정 order_set_id에 해당하는 order_detail들의 상품명, 갯수, 구매 당시 가격, 상태 이름을 dto형태로 List에 담아 반환
     * @param orderSetId
     * @return List<OrderDetailDto>
     */
    public List<OrderDetailDto> getOrderDetailList(Long orderSetId) {
        return orderDetailDao.getOrderDetailList(GetSessionFactory.getInstance().openSession(), orderSetId);
    }

    /**
     * orderset의 총 결제 금액을 반환
     * @param orderDetailDtoList
     * @return 총 결제 금액
     */
    public long getOrderSetTotalBuyPrice(List<OrderDetailDto> orderDetailDtoList, String status) {
        long result = 0L;
        for(OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if(orderDetailDto.getStatus().equals(status)) {
                result += orderDetailDto.getBuyPrice() * orderDetailDto.getItemQuantity();
            }
        }
        return result;
    }

    public long getConsumerTotalBuyPrice(Long consumerId) {
        return orderDetailDao.getConsumerTotalBuyPrice(GetSessionFactory.getInstance().openSession(), consumerId);
    }
}
