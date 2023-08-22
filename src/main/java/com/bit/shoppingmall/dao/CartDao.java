package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.exception.NotContainedAnything;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CartDao {
    public List<CartItem> selectAll(SqlSession session) {
        return session.selectList("cartItem.selectAll");
    }

    public void insertCartItem(CartItem cartItem, SqlSession session) {
        session.insert("cartItem.insert", cartItem);
        session.commit();
    }

    public List<CartItem> selectById(long loginedId, SqlSession session) throws NotContainedAnything {
        List<CartItem> cartItemsLogined = session.selectList("cartItem.selectListById", loginedId);
        return cartItemsLogined;
    }

    public CartItem selectByItemId(Long itemId, SqlSession session) throws NotContainedAnything {
        CartItem cartItemContained = session.selectOne("cartItem.selectByItemId", itemId);
        return cartItemContained;
    }

    public void updateQuantity(CartItem cartItem, SqlSession session) {
        session.update("cartItem.updateQuantity", cartItem);
        session.commit();
    }

    public void deleteByItemId(long itemId, SqlSession session) {
        session.delete("cartItem.deleteByItemId", itemId);
    }
}
