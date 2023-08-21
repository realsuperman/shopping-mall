package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CartService {
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /**
     * 장바구니 상품 전체 검색
     * @return List<Cart>
     */
    public List<CartItem> get() {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        return cartDao.selectAll(session);
    }

    /**
     * 현재 로그인된 consumer의 장바구니 상품 추가하기
     * @param cartItem
     */
    public void register(CartItem cartItem) {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        cartDao.insertCartItem(cartItem, session);
    }

    /**
     * 현재 로그인된 consumer의 장바구니 목록 조회하기
     * @param loginedId
     */
    public List<CartItem> get(long loginedId) {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        return cartDao.selectById(loginedId, session);
    }
}
