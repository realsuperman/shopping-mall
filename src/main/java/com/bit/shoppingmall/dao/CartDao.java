package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.CartItem;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CartDao {
    public List<CartItem> selectAll(SqlSession session) {
        return session.selectList("cartItem.selectAll");
    }

    public void insertCartItem(CartItem cartItem, SqlSession session) {
        session.insert("cartItem.insert", cartItem);
        session.commit();
        session.close();
    }
}
