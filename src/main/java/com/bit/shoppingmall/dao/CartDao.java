package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.dto.OrderItemDto;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

public class CartDao {
    public List<CartItem> selectAll(SqlSession session) {
        return session.selectList("cartItem.selectAll");
    }

    public void insertCartItem(CartItem cartItem, SqlSession session) {
        session.insert("cartItem.insert", cartItem);
    }

    public List<CartItem> selectById(long loginedId, SqlSession session) {
        List<CartItem> cartItemsLogined = session.selectList("cartItem.selectListById", loginedId);
        return cartItemsLogined;
    }

    public CartItem selectByItemId(Long itemId, Long loginedId, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("consumerId", loginedId);
        CartItem cartItemContained = session.selectOne("cartItem.selectByItemId", map);
        return cartItemContained;
    }

    public void updateQuantity(CartItem cartItem, Long loginedId, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("itemId", cartItem.getItemId());
        map.put("itemQuantity", cartItem.getItemQuantity());
        map.put("consumerId", loginedId);
        session.update("cartItem.updateQuantity", cartItem);
    }

    public int deleteByItemId(long itemId, long consumerId, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("consumerId", consumerId);
        return session.delete("cartItem.deleteByItemId", map);
    }

    public void updateByItemId(long itemId, long loginedId, long cnt, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("consumerId", loginedId);
        map.put("itemQuantity", cnt);
        session.update("cartItem.updateByItemId", map);
    }

    public int deleteByCartId(SqlSession sqlSession, List<OrderItemDto> list) {
        return sqlSession.delete("cartItem.deleteByCartId", list);
    }

    public List<CartItem> selectByIdLimit5(long loginedId, long start, long end, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("consumerId", loginedId);
        map.put("start", start);
        map.put("end", end);
        return session.selectList("cartItem.selectByIdLimit5", map);
    }
}
