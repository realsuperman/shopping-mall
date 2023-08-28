package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.OrderDetail;
import com.bit.shoppingmall.domain.OrderSet;
import com.bit.shoppingmall.dto.OrderCancelDto;
import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.KakaoPayProcess;
import com.bit.shoppingmall.vo.KakaoPayCancelVO;
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

            // TODO : map으로 바꿀 필요 없이 바로 for
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
                    .orderCode(kakaoPayVO.getTid()) // 취소를 위해서는 kakao tid 저장해야함
                    .orderAddress(orderInfoDto.getOrderAddress())
                    .orderPhoneNumber(orderInfoDto.getOrderPhoneNumber())
                    .build()
            );

            // TODO : 메소드 혹은 스트림
            // insert order_detail
            /*List<OrderDetail> orderDetailList = new ArrayList<>();
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
            }*/
            List<OrderDetail> orderDetailList = cargoToDeliver.stream()
                    .flatMap(cargo -> orderItemDtoList.stream()
                            .filter(orderItemDto -> cargo.getItemId().equals(orderItemDto.getItemId()))
                            .map(orderItemDto -> OrderDetail.builder()
                                    .orderSetId(orderSetId)
                                    .buyPrice(orderItemDto.getItemPrice())
                                    .cargoId(cargo.getCargoId())
                                    .statusId(6L)
                                    .build()
                            )
                    ).collect(Collectors.toList());

            orderDetailDao.insertOrderDetail(sqlSession, orderDetailList);

            // delete cart_item
            cartDao.deleteByCartId(sqlSession, orderItemDtoList);

            int kakaoPayResponse = KakaoPayProcess.approve(kakaoPayVO);

            if (kakaoPayResponse != 200) {
                sqlSession.rollback();
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
                KakaoPayProcess.cancel(KakaoPayCancelVO.builder()
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

    public void cancelOrder(Long orderSetId, List<OrderCancelDto> orderCancelDtoList) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession();
        try {
            for(OrderCancelDto orderCancelDto: orderCancelDtoList) {
                // orderSetId, itemId, itemQuantity를 통해 주문 취소할 cargo를 찾는다
                Map<String, Long> map = new HashMap<>();
                map.put("orderSetId", orderSetId);
                map.put("itemId", orderCancelDto.getItemId());
                map.put("itemQuantity", orderCancelDto.getItemQuantity());
                List<Map<String, Long>> cargoAndOrderDetails = orderDetailDao.getCancelOrderDetailIdAndCargoId(sqlSession, map);

                // 각 cargo와 order_detail의 status_id를 수정한다
                for(Map<String, Long> cargoAndOrderDetail: cargoAndOrderDetails) {
                    Map<String, Long> cargo = new HashMap<>();
                    cargo.put("statusId", 3L);
                    cargo.put("cargoId", cargoAndOrderDetail.get("cargoId"));
                    cargoDao.updateCargoStatusByCargoId(sqlSession, cargo);

                    Map<String, Long> orderDetail = new HashMap<>();
                    orderDetail.put("orderDetailId", cargoAndOrderDetail.get("orderDetailId"));
                    orderDetail.put("statusId", 7L);
                    orderDetailDao.updateOrderDetailStatusByOrderDetailId(sqlSession, orderDetail);
                }
            }

            long cancelAmount = 0L;
            for(OrderCancelDto orderCancelDto: orderCancelDtoList) {
                cancelAmount += (orderCancelDto.getBuyPrice() * orderCancelDto.getItemQuantity());
            }

            // kakao 결제 취소
            int kakaoPayCancelResponse = KakaoPayProcess.cancel(KakaoPayCancelVO.builder()
                    .cancelAmount((int) cancelAmount)
                    .tid(orderSetDao.selectByOrderSetId(sqlSession, orderSetId).getOrderCode())
                    .cid("TC0ONETIME")
                    .cancelAmount((int) cancelAmount)
                    .build()
            );

            if(kakaoPayCancelResponse != 200) {
                sqlSession.rollback();
                throw new MessageException("결제 취소가 실패했습니다");
            }

            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
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
