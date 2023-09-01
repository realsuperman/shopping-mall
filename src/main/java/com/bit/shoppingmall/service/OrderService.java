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

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

@AllArgsConstructor
public class OrderService {

    private final OrderDetailDao orderDetailDao;
    private final OrderSetDao orderSetDao;
    private final CartDao cartDao;
    private final CargoDao cargoDao;

    private static final Logger logger = Logger.getLogger("Order Service");

    private final Long CARGO_STATUS_STOCK = 3L;
    private final Long CARGO_STATUS_RELEASE = 4L;
    private final Long ORDER_STATUS_PAY_COMPLETE = 6L;
    private final Long ORDER_STATUS_ORDER_CANCEL = 7L;

    public void order(Long consumerId, OrderInfoDto orderInfoDto, List<OrderItemDto> orderItemDtoList, KakaoPayVO kakaoPayVO) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession();
        try { // kakao 결제 요청 단계 이후, DB 수정 작업과 kakao 결제 승인 단계가 한 트랜잭션으로 묶인다.

            /* TODO
             cargoDao.selectCountByItemId
             cargoDao.selectCargoToDeliver

             그 item_id인 cargo가 원하는 갯수 이상 있니? -> DB 조회
             그 cargo를 원하는 갯수만큼 가져와 -> DB 조회

             =>

             그 item_id인 cargo를 다 가져와 -> DB 조회
             이후 갯수 관련 체크는 service에서?
             */

            throwExceptionIfInsufficientCargo(sqlSession, orderItemDtoList);

            List<Cargo> cargoToDeliver = getCargoToDeliver(sqlSession, orderItemDtoList);

            // update cargo_id.status
            for (Cargo cargo : cargoToDeliver) {
                Map<String, Long> cargoAndStatus = new HashMap<>();
                cargoAndStatus.put("cargoId", cargo.getCargoId());
                cargoAndStatus.put("statusId", CARGO_STATUS_RELEASE);

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

            // insert order_detail
            List<OrderDetail> orderDetailsToInsert = makeOrderDetailsToInsert(orderSetId, orderItemDtoList, cargoToDeliver);
            orderDetailDao.insertOrderDetail(sqlSession, orderDetailsToInsert);

            // delete cart_item
            cartDao.deleteByCartId(sqlSession, orderItemDtoList);

            // kakao 결제 승인 phase
            if (KakaoPayProcess.approve(kakaoPayVO) != HttpServletResponse.SC_OK) {
                throw new MessageException("결제가 실패했습니다");
            }
            sqlSession.commit();
        } catch (MessageException e) {
            sqlSession.rollback();
            throw e;
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void cancelOrder(Long orderSetId, List<OrderCancelDto> orderCancelDtoList) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession();
        try {
            List<Map<String, Long>> cargoAndOrderDetails = new ArrayList<>();
            // orderSetId, itemId, itemQuantity를 통해 주문 취소할 cargo를 찾는다
            for(OrderCancelDto orderCancelDto: orderCancelDtoList) {
                Map<String, Long> map = new HashMap<>();
                map.put("orderSetId", orderSetId);
                map.put("itemId", orderCancelDto.getItemId());
                map.put("itemQuantity", orderCancelDto.getItemQuantity());
                cargoAndOrderDetails.addAll(orderDetailDao.getCancelOrderDetailIdAndCargoId(sqlSession, map));
            }

            // 각 cargo와 order_detail의 status_id를 수정한다
            for(Map<String, Long> cargoAndOrderDetail: cargoAndOrderDetails) {
                Map<String, Long> cargo = new HashMap<>();
                cargo.put("statusId", CARGO_STATUS_STOCK);
                cargo.put("cargoId", cargoAndOrderDetail.get("cargoId"));
                cargoDao.updateCargoStatusByCargoId(sqlSession, cargo);

                Map<String, Long> orderDetail = new HashMap<>();
                orderDetail.put("orderDetailId", cargoAndOrderDetail.get("orderDetailId"));
                orderDetail.put("statusId", ORDER_STATUS_ORDER_CANCEL);
                orderDetailDao.updateOrderDetailStatusByOrderDetailId(sqlSession, orderDetail);
            }

            long cancelAmount = 0L;
            for(OrderCancelDto orderCancelDto: orderCancelDtoList) {
                cancelAmount += (orderCancelDto.getBuyPrice() * orderCancelDto.getItemQuantity());
            }

            logger.info("tid: "+orderSetDao.selectByOrderSetId(sqlSession, orderSetId).getOrderCode());

            KakaoPayCancelVO kakaoPayCancelVO = KakaoPayCancelVO.builder()
                    .tid(orderSetDao.selectByOrderSetId(sqlSession, orderSetId).getOrderCode())
                    .cid("TC0ONETIME")
                    .cancelAmount((int) cancelAmount)
                    .cancelTaxFreeAmount((int) cancelAmount)
                    .build();

            // kakao 결제 취소
            if(KakaoPayProcess.cancel(kakaoPayCancelVO) != HttpServletResponse.SC_OK) {
                throw new MessageException("결제 취소가 실패했습니다");
            }

            sqlSession.commit();
        } catch (MessageException e) {
            sqlSession.rollback();
            throw e;
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    // TODO : 동적 쿼리로 DB 한 번만 접근해서 결과 얻을 수 있을 것 같은데
    public void throwExceptionIfInsufficientCargo(SqlSession sqlSession, List<OrderItemDto> orderItemDtoList) {
        for(OrderItemDto orderItemDto: orderItemDtoList) {
            // item_id를 가지는 cargo의 count가 원하는 갯수 미만이면 exception -> rollback
            if(cargoDao.selectCountByItemId(sqlSession, orderItemDto.getItemId()) < orderItemDto.getItemQuantity()) {
                throw new MessageException("재고가 부족합니다");
            }
        }
    }

    public List<Cargo> getCargoToDeliver(SqlSession sqlSession, List<OrderItemDto> orderItemDtoList) {
        List<Cargo> cargoToDeliver = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) {
            Map<String, Long> idAndQuantity = new HashMap<>();
            idAndQuantity.put("itemId", orderItemDto.getItemId());
            idAndQuantity.put("itemQuantity", orderItemDto.getItemQuantity());

            cargoToDeliver.addAll(cargoDao.selectCargoToDeliver(sqlSession, idAndQuantity));
        }
        return cargoToDeliver;
    }

    public List<OrderDetail> makeOrderDetailsToInsert(Long orderSetId, List<OrderItemDto> orderItemDtoList, List<Cargo> cargoToDeliver) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) { // item_id buy_price
            for (Cargo cargo : cargoToDeliver) { // cargo_id, item_id
                if (cargo.getItemId().equals(orderItemDto.getItemId())) {
                    orderDetailList.add(OrderDetail.builder()
                            .orderSetId(orderSetId)
                            .buyPrice(orderItemDto.getItemPrice())
                            .cargoId(cargo.getCargoId())
                            .statusId(ORDER_STATUS_PAY_COMPLETE)
                            .build());
                }
            }
        }
        return orderDetailList;
    }
}
