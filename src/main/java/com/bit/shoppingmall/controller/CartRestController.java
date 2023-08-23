package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.service.CartService;
import com.bit.shoppingmall.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartRestController extends HttpServlet {
    private final CartService cartService;
    private final ItemService itemService;

    public CartRestController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("call doDelete...");
        long sessionId = 1;
        try {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            JSONObject jsonData = new JSONObject(requestBody.toString());

            long itemId = Long.parseLong(jsonData.getString("itemId"));
            System.out.println("Received itemId: " + itemId);
            cartService.removeByItemId(itemId, sessionId);
            List<CartItem> cartItems = cartService.get(sessionId);
            List<CartItemDto> cartItemDtos = new ArrayList<>();
            for(CartItem cartItem : cartItems) {
                Item found = itemService.selectItemById(cartItem.getItemId());
                long totalPrice = cartService.calTotalPricePerItem(found.getItemPrice(), cartItem.getItemQuantity());
                CartItemDto cartItemDto = CartItemDto.builder()
                        .itemId(found.getItemId())
                        .categoryId(found.getCategoryId())
                        .itemName(found.getItemName())
                        .itemPrice(found.getItemPrice())
                        .itemImagePath(found.getItemImagePath())
                        .totalPrice(totalPrice)
                        .itemQuantity(cartItem.getItemQuantity())
                        .build();
                cartItemDtos.add(cartItemDto);
            }

            // Jackson ObjectMapper를 사용하여 Java 객체를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(cartItemDtos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}