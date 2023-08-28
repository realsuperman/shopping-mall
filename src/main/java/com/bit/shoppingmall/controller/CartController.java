package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.exception.MessageException;

import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.global.Pageable;
import com.bit.shoppingmall.service.CartService;
import com.bit.shoppingmall.service.ItemService;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CartController extends HttpServlet {
    private Logger cart_log = Logger.getLogger("cart");
    private CartService cartService;
    private ItemService itemService;
    private final String fileName = "cart";

    public CartController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");
        long loginedId = consumer.getConsumerId();

        Pageable pageable = cartService.getPagingList(loginedId);
        List<CartItem> cartItemsMetaInfos = cartService.getLimit5(loginedId, pageable.getPageStartCartItem(), pageable.getPageLastCartItem());
        List<CartItem> cartItemAll = cartService.get(loginedId);
        List<CartItemDto> foundItemsAll = new ArrayList<>();
        if(cartItemAll.size() == 0) {
            request.setAttribute("errMsg", "장바구니에 담긴 상품이 없습니다");
        } else {
            for (CartItem cartItem : cartItemAll) {
                Item foundItem = itemService.selectItemById(cartItem.getItemId());
                Long totalPricePerItem = cartService.calTotalPricePerItem(foundItem.getItemPrice(), cartItem.getItemQuantity());
                CartItemDto cartItemDto = CartItemDto.builder()
                        .itemId(foundItem.getItemId())
                        .categoryId(foundItem.getCategoryId())
                        .itemName(foundItem.getItemName())
                        .itemPrice(foundItem.getItemPrice())
                        .itemImagePath(foundItem.getItemImagePath())
                        .totalPrice(totalPricePerItem)
                        .itemQuantity(cartItem.getItemQuantity())
                        .cartId(cartItem.getCartId())
                        .build();
                foundItemsAll.add(cartItemDto);
            }
        }
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
                                        .cartId(cartItemsMetaInfo.getCartId())
                                        .build();
            foundItems.add(cartItemDto);
        }

//        response.setCharacterEncoding("UTF-8");
        request.setAttribute("pageable", pageable);
        request.setAttribute("cartItems", foundItems);
        request.setAttribute("cartItemsAll", foundItemsAll);
        //에러 처리
//        cart_log.info(e.getMessage());


        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        cart_log.info("CartController doPost...");
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");
        long loginedId = consumer.getConsumerId();
        String jsonString = request.getParameter("putInCartDto");
        System.out.println("jsonString: " + jsonString);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            long itemId = jsonObject.getLong("itemId");
            String itemName = jsonObject.getString("itemName");
            long itemPrice = jsonObject.getLong("itemPrice");
            long itemQuantity = jsonObject.getLong("itemQuantity");
            String itemImagePath = jsonObject.getString("itemImagePath");

            cart_log.info("itemId: " + itemId);
            cart_log.info("itemName: " + itemName);
            cart_log.info("itemPrice: " + itemPrice);
            cart_log.info("itemQuantity: " + itemQuantity);
            cart_log.info("itemImagePath: " + itemImagePath);

            CartItem newCartItem = CartItem.builder()
                                    .itemId(itemId)
                                    .itemQuantity(itemQuantity)
                                    .consumerId(loginedId)
                                    .build();

            cartService.register(newCartItem);

            String originalString = "장바구니에 해당 상품을 담았습니다.";
            String encodedString = URLEncoder.encode(originalString, "UTF-8");
            response.sendRedirect("/itemDetail?itemId=" + itemId +"&sucMsg=" + encodedString);
        } catch (JSONException e) {
            e.printStackTrace();
            // 예외 처리
        } catch (Exception e) {
            request.setAttribute("errMsg", "장바구니 상품 담기에 실패했습니다.");
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("CartController doPut...");
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");
        long loginedId = consumer.getConsumerId();
        try {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            JSONObject jsonData = new JSONObject(requestBody.toString());
            long itemId = Long.parseLong(jsonData.getString("itemId"));
            long cnt = Long.parseLong(jsonData.getString("cnt"));
            cartService.modifyByItemId(itemId, loginedId, cnt);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
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
        } catch (MessageException e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
    }
}