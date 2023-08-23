package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.OrderSet;
import com.bit.shoppingmall.dto.OrderDetailDto;
import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.dto.OrderSetDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class OrderServicetest {

    OrderDetailDao orderDetailDao = new OrderDetailDao();
    OrderSetDao orderSetDao = new OrderSetDao();
    CartDao cartDao = new CartDao();
    CargoDao cargoDao = new CargoDao();

    OrderService orderService = new OrderService(orderDetailDao, orderSetDao, cartDao, cargoDao);

    Logger logger = Logger.getLogger("OrderServiceTest");

    @Test
    @DisplayName("장바구니에서 구매 성공")
    public void orderFromCartSuccessTest() {
        Long consumerId = 1L; // 실 요청에서는 session에서 꺼내서 controller로 전달

        OrderInfoDto orderInfoDto = OrderInfoDto.builder()
                .orderAddress("테스트 주소")
                .orderPhoneNumber("01012345678")
                .build();

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(OrderItemDto.builder()
                .itemId(2L)
                .itemQuantity(3L)
                .cartId(1L)
                .itemPrice(501)
                .build());

        orderItemDtoList.add(OrderItemDto.builder()
                .itemId(17L)
                .itemQuantity(1L)
                .cartId(2L)
                .itemPrice(501)
                .build());

        SqlSession beforeSession = GetSessionFactory.getInstance().openSession();

        List<OrderSetDto> orderSetBeforeOrder = orderSetDao.getConsumerOrderSetDtoList(beforeSession, consumerId);
        logger.info("orderSetBeforeOrder: "+orderSetBeforeOrder.size());

        List<OrderDetailDto> orderDetailBeforeOrder = new ArrayList<>();
        for(OrderSetDto orderSetDto: orderSetBeforeOrder) {
            orderDetailBeforeOrder.addAll(orderDetailDao.getOrderDetailList(beforeSession, orderSetDto.getOrderSetId()));
        }
        logger.info("orderDetailBeforeOrder: "+orderDetailBeforeOrder.size());

        List<CartItem> cartBeforeOrder = cartDao.selectById(consumerId, beforeSession);
        logger.info("cartBeforeOrder: "+cartBeforeOrder.size());

        List<Cargo> cargoNotOrderedBeforeOrder = new ArrayList<>();
        for(OrderItemDto orderItemDto: orderItemDtoList) {
            Map<String, Long> itemIdAndStatusId = new HashMap<>();
            itemIdAndStatusId.put("itemId", orderItemDto.getItemId());
            itemIdAndStatusId.put("statusId", 4L);

            cargoNotOrderedBeforeOrder.addAll(cargoDao.selectCargoByItemIdAndNotStatusId(beforeSession, itemIdAndStatusId));
        }
        logger.info("cargoNotOrderedBeforeOrder: "+cargoNotOrderedBeforeOrder.size());

        beforeSession.close();

        ////////////////////////////////////////////////////////////////
        orderService.order(consumerId, orderInfoDto, orderItemDtoList);
        ////////////////////////////////////////////////////////////////

        SqlSession afterSession = GetSessionFactory.getInstance().openSession();

        List<OrderSetDto> orderSetAfterOrder = orderSetDao.getConsumerOrderSetDtoList(afterSession, consumerId);
        logger.info("orderSetAfterOrder: "+orderSetAfterOrder.size());

        List<OrderDetailDto> orderDetailAfterOrder = new ArrayList<>();
        for(OrderSetDto orderSetDto: orderSetAfterOrder) {
            orderDetailAfterOrder.addAll(orderDetailDao.getOrderDetailList(afterSession, orderSetDto.getOrderSetId()));
        }
        logger.info("orderDetailAfterOrder: "+orderDetailAfterOrder.size());

        List<CartItem> cartAfterOrder = cartDao.selectById(consumerId, afterSession);

        List<Cargo> cargoNotOrderedAfterOrder = new ArrayList<>();
        for(OrderItemDto orderItemDto: orderItemDtoList) {
            Map<String, Long> itemIdAndStatusId = new HashMap<>();
            itemIdAndStatusId.put("itemId", orderItemDto.getItemId());
            itemIdAndStatusId.put("statusId", 4L);

            cargoNotOrderedAfterOrder.addAll(cargoDao.selectCargoByItemIdAndNotStatusId(afterSession, itemIdAndStatusId));
        }
        logger.info("cargoNotOrderedAfterOrder: "+cargoNotOrderedAfterOrder.size());

        afterSession.close();

        Assertions.assertEquals(orderSetBeforeOrder.size()+1, orderSetAfterOrder.size());
        Assertions.assertEquals(orderDetailBeforeOrder.size() + orderItemDtoList.stream().mapToLong(OrderItemDto::getItemQuantity).sum(), orderDetailAfterOrder.size());
        Assertions.assertEquals(cartBeforeOrder.size(), cartAfterOrder.size() + orderItemDtoList.size());
        Assertions.assertEquals(cargoNotOrderedBeforeOrder.size(), cargoNotOrderedAfterOrder.size() + orderItemDtoList.stream().mapToLong(OrderItemDto::getItemQuantity).sum());
    }

    @Test
    @DisplayName("장바구니에서 구매 실패")
    public void orderFromCartFailureTest() {

    }

    @Test
    @DisplayName("상품 상세 정보에서 구매 성공")
    public void orderFromItemSuccessTest() {

    }

    @Test
    @DisplayName("상품 상세 정보에서 구매 실패")
    public void orderFromItemFailureTest() {

    }
}
