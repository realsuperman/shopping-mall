package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.domain.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class CartItemServiceTest {
    private CartService cartService;

    @BeforeEach
    public void before() {
        cartService = new CartService(new CartDao());
    }

    @DisplayName("장바구니 상품 모두 가져오기")
    @Test
    void test_selectAll() {
        List<CartItem> cartItems = cartService.get();
        int itemSize = cartItems.size();
        assertEquals(1, itemSize);
    }

    @DisplayName("현재 로그인된 사용자의 장바구니에 상품 추가하기")
    @Test
    void test_insert_cartItem() {
        long itemId = 17L;
        long itemQuantity = 100L;
        long cartId = 3L;
        long sessionId = 1L;
        CartItem cartItem = new CartItem(itemId, itemQuantity, cartId, sessionId);
        cartService.register(cartItem);

        List<CartItem> cartItems = cartService.get();
        int itemSize = cartItems.size();
        assertEquals(3, itemSize);
    }

    @DisplayName("현재 로그인된 사용자의 장바구니 상품 목록 조회")
    @Test
    void test_select_cartItem_logined() {
        long sessionId = 1L;
        List<CartItem> cartItemsLogined = cartService.get(sessionId);
        int itemSize = cartItemsLogined.size();
        assertEquals(2, itemSize);
    }

    @DisplayName("이미 장바구니에 있는 상품을 중복으로 담을 때 수량 업데이트")
    @Test
    void test_update_already_contained() {
        long itemId = 17L;
        long itemQuantity = 100L;
        long cartId = 4L;
        long sessionId = 1L;
        CartItem cartItem = new CartItem(itemId, itemQuantity, cartId, sessionId);
        boolean checkValid = cartService.checkAlreadyContained(cartItem);
        if(checkValid) {
            cartService.modifyQuantity(cartItem);
        } else {
            cartService.register(cartItem);
        }

        CartItem cartItem1 = cartService.getByItemId(cartItem.getItemId());
        assertEquals(200, cartItem1.getItemQuantity());
    }
}
