package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.CartDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import org.apache.ibatis.jdbc.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class CartItemServiceTest {
    private CartService cartService;
    private ItemService itemService;

    @BeforeEach
    public void before() {
        cartService = new CartService(new CartDao());
        itemService = new ItemService(new ItemDao());
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
        UUID cartId = UUID.randomUUID();
        long sessionId = 1L;
        CartItem cartItem = CartItem.builder()
                        .itemId(itemId)
                        .itemQuantity(itemQuantity)
                        .consumerId(sessionId)
                        .build();
        cartService.register(cartItem);

        List<CartItem> cartItems = cartService.get();
        int itemSize = cartItems.size();
        assertEquals(1, itemSize);
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
        UUID cartId = UUID.randomUUID();
        long sessionId = 1L;
        CartItem cartItem = CartItem.builder()
                .itemId(itemId)
                .itemQuantity(itemQuantity)
                .consumerId(sessionId)
                .build();
        boolean checkValid = cartService.checkAlreadyContained(cartItem);
        if(checkValid) {
            cartService.modifyQuantity(cartItem);
        } else {
            cartService.register(cartItem);
        }

        CartItem cartItem1 = cartService.getByItemId(cartItem.getItemId());
        assertEquals(200, cartItem1.getItemQuantity());
    }

    @DisplayName("장바구니 각 상품당 수량을 고려한 총 가격 구하기")
    @Test
    public void test_cal_totalPrice_per_item() {
        List<CartItem> cartItems = cartService.get(1);
        Map<Long, CartItemDto> map = new HashMap<>();

        for(CartItem cartItem : cartItems) {
            Item foundItem = itemService.selectItemById(cartItem.getItemId());
            Long totalPricePerItem = cartService.calTotalPricePerItem(foundItem.getItemPrice(), cartItem.getItemQuantity());
            CartItemDto cartItemDto = CartItemDto.builder()
                    .itemId(foundItem.getItemId())
                    .categoryId(foundItem.getCategoryId())
                    .itemName(foundItem.getItemName())
                    .itemPrice(foundItem.getItemPrice())
                    .itemImagePath(foundItem.getItemImagePath())
                    .totalPrice(totalPricePerItem)
                    .build();
            map.put(foundItem.getItemId(), cartItemDto);
        }

        assertEquals(1500, map.get(1L).getTotalPrice());
    }

    @DisplayName("itemId로 장바구니에 담은 상품 제거")
    @Test
    public void test_remove_by_id() {
        long itemId = 1;
        cartService.removeByItemId(itemId);
        CartItem found = cartService.getByItemId(itemId);
        assertEquals(null, found);
    }
}
