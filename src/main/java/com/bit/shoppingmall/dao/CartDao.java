package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.exception.NotContainedAnything;
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

    public CartItem selectByItemId(Long itemId, SqlSession session) {
        CartItem cartItemContained = session.selectOne("cartItem.selectByItemId", itemId);
        return cartItemContained;
    }

    public void updateQuantity(CartItem cartItem, SqlSession session) {
        session.update("cartItem.updateQuantity", cartItem);
    }

    public void deleteByItemId(long itemId, long consumerId, SqlSession session) {
        Map<String, Long> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("consumerId", consumerId);
        session.delete("cartItem.deleteByItemId", map);
    }
}
