package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.exception.NoSuchDataException;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CartService;
import com.bit.shoppingmall.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CartRestController extends HttpServlet {
    private Logger cart_log = Logger.getLogger("cartItemList");
    private final CartService cartService;
    private final ItemService itemService;

    private final String fileName = "cartItemList";

    public CartRestController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("call doGet...");
        Consumer loginedUser = (Consumer)request.getSession().getAttribute("login_user");
        long sessionId = loginedUser.getConsumerId();
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
        cart_log.info("cartItemDtos: " + cartItemDtos);

        request.setAttribute("cartItems", cartItemDtos);

        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher(LabelFormat.PREFIX.label()+ fileName +LabelFormat.SUFFIX.label());
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("call doDelete...");
        Consumer loginedUser = (Consumer)request.getSession().getAttribute("login_user");
        long sessionId = loginedUser.getConsumerId();

        try {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            JSONObject jsonData = new JSONObject(requestBody.toString());
            long itemId = Long.parseLong(jsonData.getString("itemId"));
            cartService.removeByItemId(itemId, sessionId);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (NoSuchDataException e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
    }
}