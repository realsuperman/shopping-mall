package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.OrderDetail;
import com.bit.shoppingmall.dto.OrderInfoDto;
import com.bit.shoppingmall.dto.OrderDetailDto;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class OrderDetailDao {
    public OrderInfoDto getOrderInfo(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectOne("order.getOrderInfo", orderSetId);
    }

    public List<OrderDetailDto> getOrderDetailList(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectList("order.getOrderDetailList", orderSetId);
    }

    public Long getConsumerTotalBuyPrice(SqlSession sqlSession, Long consumerId) {
        return sqlSession.selectOne("order.getConsumerTotalBuyPrice", consumerId);
    }

    public long getConsumerId(SqlSession sqlSession, Long orderSetId) {
        return sqlSession.selectOne("order.getConsumerId", orderSetId);
    }

    public int updateOrderDetailStatusByOrderDetailId(SqlSession sqlSession, Map<String, Long> map) {
        return sqlSession.update("order.updateOrderDetailStatusByOrderDetailId", map);
    }

    public int insertOrderDetail(SqlSession sqlSession, List<OrderDetail> list) {
        return sqlSession.insert("order.insertOrderDetail", list);
    }
}
