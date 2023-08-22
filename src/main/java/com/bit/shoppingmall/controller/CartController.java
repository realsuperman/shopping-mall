package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.exception.NotContainedAnything;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CartService;
import com.bit.shoppingmall.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartController extends HttpServlet {
    private final CartService cartService;
    private final ItemService itemService;

    private final String fileName = "cart";

    public CartController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long loginedId = 1L;//하드코딩
        try {
            List<CartItem> cartItemsMetaInfos = cartService.get(loginedId);
            List<CartItemDto> foundItems = new ArrayList<>();
            for(CartItem cartItemsMetaInfo : cartItemsMetaInfos) {
                Item foundItem = itemService.selectItemById(cartItemsMetaInfo.getItemId());
                Long totalPricePerItem = cartService.calTotalPricePerItem(foundItem.getItemPrice(), cartItemsMetaInfo.getItemQuantity());
                CartItemDto cartItemDto = CartItemDto.builder()
                                            .itemId(foundItem.getItemId())
                                            .categoryId(foundItem.getCategoryId())
                                            .itemName(foundItem.getItemName())
                                            .itemPrice(foundItem.getItemPrice())
                                            .itemImagePath(foundItem.getItemImagePath())
                                            .totalPrice(totalPricePerItem)
                                            .itemQuantity(cartItemsMetaInfo.getItemQuantity())
                                            .build();
                foundItems.add(cartItemDto);
            }

            request.setAttribute("cartItems", foundItems);
        } catch (NotContainedAnything e) {
            //에러 처리
        }

        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}