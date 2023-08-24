package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.exception.NoSuchDataException;
import com.bit.shoppingmall.exception.NotContainedAnything;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.Pageable;
import com.google.api.Page;
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
        List<CartItem> list = cartDao.selectAll(session);
        session.close();
        return list;
    }

    /**
     * 현재 로그인된 consumer의 장바구니 상품 추가하기
     * @param cartItem
     */
    public void register(CartItem cartItem) {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartDao.insertCartItem(cartItem, session);
        session.close();
    }

    /**
     * 현재 로그인된 consumer의 장바구니 목록 조회하기
     * @param loginedId
     * @return List<CartItem>
     */
    public List<CartItem> get(long loginedId) throws NotContainedAnything {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        List<CartItem> foundLists = cartDao.selectById(loginedId, session);
        session.close();
        if(foundLists.isEmpty()) {
            throw new NotContainedAnything("장바구니에 담긴 상품이 없습니다.");
        }
        return foundLists;
    }

    /**
     * consumer가 장바구니에 담은 상품이 이미 담겨져 있는지 체크
     * @param cartItem
     * @return boolean
     */
    public boolean checkAlreadyContained(CartItem cartItem) {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        CartItem cartItemContained = cartDao.selectByItemId(cartItem.getItemId(), session);
        session.close();
        if(cartItemContained == null) {
            return false;
        }
        return true;
    }

    /**
     * 이미 담겨진 상품의 수량 업데이트
     * @param cartItem
     */
    public void modifyQuantity(CartItem cartItem, Long loginedId) {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartItem.increaseQuantity(cartItem.getItemQuantity());
        cartDao.updateQuantity(cartItem, loginedId, session);
        session.close();
    }

    /**
     * itemId로 장바구니에 담기 상품 조회
     * @param itemId
     * @return CartItem
     */
    public CartItem getByItemId(Long itemId) throws NotContainedAnything {
        SqlSession session = GetSessionFactory.getInstance().openSession();
        CartItem found = cartDao.selectByItemId(itemId, session);
        session.close();
        return found;
    }

    /**
     * 장바구니에 담긴 각 상품당 수량을 고려한 총 가격 구하기
     * @param itemPrice
     * @param itemQuantity
     * @return Long
     */
    public Long calTotalPricePerItem(long itemPrice, long itemQuantity) {
        return itemPrice * itemQuantity;
    }

    /**
     * 각 상품의 고유 itemId로 장바구니에 담긴 해당 상품 제거
     * @param itemId
     * @param consumerId
     */
    public void removeByItemId(long itemId, long consumerId) throws NoSuchDataException {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        int checkValid = cartDao.deleteByItemId(itemId, consumerId, session);
        if(checkValid == 0) {
            throw new NoSuchDataException("제거 실패");
        }
        session.close();
    }

    /**
     * 현재 로그인된 사용자가 장바구니에서 수량을 바꿀 때마다 update
     * @param itemId
     * @param loginedId
     * @param cnt
     */
    public void modifyByItemId(long itemId, long loginedId, long cnt) {
        SqlSession session = GetSessionFactory.getInstance().openSession(true);
        cartDao.updateByItemId(itemId, loginedId, cnt, session);
        session.close();
    }

    public Pageable getPagingList(int page, long loginedId) {
        Pageable pageable = new Pageable();
        pageable.of(page, loginedId);
        return pageable;
    }
}
