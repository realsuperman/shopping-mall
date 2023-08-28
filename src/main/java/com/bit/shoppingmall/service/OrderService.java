package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.OrderDetail;
import com.bit.shoppingmall.domain.OrderSet;
import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.KakaoPayProcess;
import com.bit.shoppingmall.vo.KakaoPayCancelCancelVO;
import com.bit.shoppingmall.vo.KakaoPayVO;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderService {

    private final OrderDetailDao orderDetailDao;
    private final OrderSetDao orderSetDao;
    private final CartDao cartDao;
    private final CargoDao cargoDao;

    private static final Logger logger = Logger.getLogger("Order Service");

    public void order(Long consumerId, OrderInfoDto orderInfoDto, List<OrderItemDto> orderItemDtoList, KakaoPayVO kakaoPayVO) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession();
        try {
            // cargo count가 부족한 item_id의 list가 비어 있지 않다면 exception
            if (!getInSufficientItemIds(sqlSession, orderItemDtoList).isEmpty()) {
                throw new MessageException("재고가 부족합니다"); // alert면 MessageException
            }

            // select cargo to update cargo.status
            List<Cargo> cargoToDeliver = new ArrayList<>();
            for (OrderItemDto orderItemDto : orderItemDtoList) {
                Map<String, Long> idAndQuantity = new HashMap<>();
                idAndQuantity.put("itemId", orderItemDto.getItemId());
                idAndQuantity.put("itemQuantity", orderItemDto.getItemQuantity());

                cargoToDeliver.addAll(cargoDao.selectCargoToDeliver(sqlSession, idAndQuantity));
            }

            // update cargo_id.status
            for (Cargo cargo : cargoToDeliver) {
                Map<String, Long> cargoAndStatus = new HashMap<>();
                cargoAndStatus.put("cargoId", cargo.getCargoId());
                cargoAndStatus.put("statusId", 4L); // TODO : 하드 코딩

                cargoDao.updateCargoStatusByCargoId(sqlSession, cargoAndStatus);
            }

            // insert order_set
            Long orderSetId = orderSetDao.insertOrderSet(sqlSession, OrderSet.builder()
                    .consumerId(consumerId)
                    .orderCode(orderInfoDto.getOrderCode())
                    .orderAddress(orderInfoDto.getOrderAddress())
                    .orderPhoneNumber(orderInfoDto.getOrderPhoneNumber())
                    .build()
            );

            // insert order_detail
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (OrderItemDto orderItemDto : orderItemDtoList) { // item_id buy_price
                for (Cargo cargo : cargoToDeliver) { // cargo_id, item_id
                    if (cargo.getItemId().equals(orderItemDto.getItemId())) {
                        orderDetailList.add(OrderDetail.builder()
                                .orderSetId(orderSetId)
                                .buyPrice(orderItemDto.getItemPrice())
                                .cargoId(cargo.getCargoId())
                                .statusId(4L)
                                .build());
                    }
                }
            }

            orderDetailDao.insertOrderDetail(sqlSession, orderDetailList);

            // delete cart_item
            cartDao.deleteByCartId(sqlSession, orderItemDtoList);

            int kakaoPayResponse = KakaoPayProcess.approve(kakaoPayVO);

            if (kakaoPayResponse != 200) {
                throw new MessageException("결제가 실패했습니다");
            }
            logger.info("kakao pay response: " + kakaoPayResponse);

            logger.info("Transaction Success");

            sqlSession.commit();
        } catch (MessageException e) {
            throw e;
        } catch (Exception e) {
            sqlSession.rollback();
            try {
                KakaoPayProcess.cancel(KakaoPayCancelCancelVO.builder()
                        .cid(kakaoPayVO.getCid())
                        .tid(kakaoPayVO.getTid())
                        .cancelAmount(kakaoPayVO.getCancelAmount())
                        .cancelTaxFreeAmount(kakaoPayVO.getCancelTaxFreeAmount())
                        .build());
            } catch (IOException ioe) {
                throw new MessageException("결제 서버와 연결이 실패했습니다");
            }
        } finally {
            sqlSession.close();
        }
    }

    public void cancelOrder(Long consumerId) {

    }

    // TODO : 동적 쿼리로 DB 한 번만 접근해서 결과 얻을 수 있을 것 같은데
    public List<Long> getInSufficientItemIds(SqlSession sqlSession, List<OrderItemDto> orderItemDtoList) {
        List<Long> result = new ArrayList<>();
        for(OrderItemDto orderItemDto: orderItemDtoList) {
            if(cargoDao.selectCountByItemId(sqlSession, orderItemDto.getItemId()) < orderItemDto.getItemQuantity()) {
                result.add(orderItemDto.getItemId());
            }
        }
        return result;
    }
}
