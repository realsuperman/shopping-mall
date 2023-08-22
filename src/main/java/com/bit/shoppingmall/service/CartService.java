package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.exception.NotContainedAnything;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CartService {
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /**
     * 테스트용 코드, 장바구니 상품 전체 검색
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
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartDao.insertCartItem(cartItem, session);
    }

    /**
     * 현재 로그인된 consumer의 장바구니 목록 조회하기
     * @param loginedId
     */
    public List<CartItem> get(long loginedId) throws NotContainedAnything {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        return cartDao.selectById(loginedId, session);
    }

    /**
     * consumer가 장바구니에 담은 상품이 이미 담겨져 있는지 체크
     * @param cartItem
     */
    public boolean checkAlreadyContained(CartItem cartItem) {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        CartItem cartItemContained = cartDao.selectByItemId(cartItem.getItemId(), session);
        if(cartItemContained == null) {
            return false;
        }
        return true;
    }

    /**
     * 이미 담겨진 상품의 수량 업데이트
     * @param cartItem
     */
    public void modifyQuantity(CartItem cartItem) {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartItem.increaseQuantity(cartItem.getItemQuantity());
        cartDao.updateQuantity(cartItem, session);
    }

    /**
     * itemId로 장바구니에 담기 상품 조회
     * @param itemId
     */
    public CartItem getByItemId(Long itemId) throws NotContainedAnything {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        return cartDao.selectByItemId(itemId, session);
    }

    /**
     * 장바구니에 담긴 각 상품당 수량을 고려한 총 가격 구하기
     * @param itemPrice
     * @param itemQuantity
     */
    public Long calTotalPricePerItem(long itemPrice, long itemQuantity) {
        return itemPrice * itemQuantity;
    }

    /**
     * 각 상품의 고유 itemId로 장바구니에 담긴 해당 상품 제거
     * @param itemId
     */
    public void removeByItemId(long itemId, long consumerId) {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartDao.deleteByItemId(itemId, consumerId, session);
    }
}
