package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.dto.OrderItemDto;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.global.Pageable;
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
import java.util.*;
import java.util.logging.Logger;

public class CartRestController extends HttpServlet {
    private Logger cart_log = Logger.getLogger("cartItemList");
    private CartService cartService;
    private ItemService itemService;
    private final String fileName = "cartItemList";
    private Pageable pageable;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }
    public CartRestController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("CartRestController call doGet...");
        Consumer loginedUser = (Consumer)request.getSession().getAttribute("login_user");
        long sessionId = loginedUser.getConsumerId();

        if(pageable == null) {
            pageable = cartService.getPagingList(sessionId);
        }
        int start = pageable.getPageStartCartItem();
        int end = pageable.getPageLastCartItem();

        List<CartItem> cartItems = cartService.getLimit5(sessionId, start, end);
        List<CartItemDto> checkedList = new ArrayList<>();
        List<CartItemDto> uncheckedList = new ArrayList<>();

        Set<Long> set = new HashSet<>();
        if(request.getSession().getAttribute("checkedIdSet") != null) {
            set = (Set<Long>) request.getSession().getAttribute("checkedIdSet");
        }
        for(CartItem cartItem : cartItems) {
            long itemId = cartItem.getItemId();
            Item item = itemService.selectItemById(cartItem.getItemId());
            Long tp = cartService.calTotalPricePerItem(item.getItemPrice(), cartItem.getItemQuantity());
            CartItemDto cid = CartItemDto.builder()
                    .itemId(itemId)
                    .categoryId(item.getCategoryId())
                    .itemName(item.getItemName())
                    .itemPrice(item.getItemPrice())
                    .itemImagePath(item.getItemImagePath())
                    .totalPrice(tp)
                    .itemQuantity(cartItem.getItemQuantity())
                    .cartId(cartItem.getCartId())
                    .build();
            if(set.contains(itemId)) {
                checkedList.add(cid);
            } else {
                uncheckedList.add(cid);
            }
        }

        List<CartItemDto> foundItemsAll = new ArrayList<>();
        List<CartItem> cartItemAll = cartService.get(sessionId);
        for(CartItem cartItem : cartItemAll) {
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

        request.setAttribute("checkedList", checkedList);
        request.setAttribute("uncheckedList", uncheckedList);
        request.setAttribute("pageable", pageable);
        request.setAttribute("cartItemsAll", foundItemsAll);

        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher(LabelFormat.PREFIX.label()+ fileName +LabelFormat.SUFFIX.label());
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("CartRestController call doPost...");
        String url = null;
        JSONObject jsonData = null;
        Set<Long> set = (Set<Long>)request.getSession().getAttribute("set");
        try {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            jsonData = new JSONObject(requestBody.toString());
            url = jsonData.getString("url");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if("/checked".equals(url)) {
            Long checkedId = null;
            try {
                checkedId = Long.parseLong(jsonData.getString("checkedId"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if(request.getSession().getAttribute("checkedIdSet") != null) {
                set = (Set<Long>)request.getSession().getAttribute("checkedIdSet");
                set.add(checkedId);
            } else {
                set = new HashSet<>();
                set.add(checkedId);
            }
            request.getSession().setAttribute("checkedIdSet", set);
        } else if("/unchecked".equals(url)){
            Long uncheckedId = null;
            try {
                uncheckedId = Long.parseLong(jsonData.getString("uncheckedId"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if(request.getSession().getAttribute("checkedIdSet") != null) {
                set = (Set<Long>)request.getSession().getAttribute("checkedIdSet");
                set.remove(uncheckedId);
            }
            request.getSession().setAttribute("checkedIdSet", set);
        }

        Consumer loginedUser = (Consumer) request.getSession().getAttribute("login_user");
        long sessionId = loginedUser.getConsumerId();
        try {
            Integer curPageNum = Integer.parseInt(jsonData.getString("curPageNum"));
            Integer prevPageNum = Integer.parseInt(jsonData.getString("prevPageNum"));
            Integer nextPageNum = Integer.parseInt(jsonData.getString("nextPageNum"));
            Integer pageStartCartItem = Integer.parseInt(jsonData.getString("pageStartCartItem"));
            Integer pageLastCartItem = Integer.parseInt(jsonData.getString("pageLastCartItem"));
            Integer flag = jsonData.getInt("flag");

            pageable = new Pageable(sessionId);
            if (flag == 0) {
                pageable.fixCurPage(prevPageNum);
                pageable.of(prevPageNum, pageStartCartItem, pageLastCartItem);
            } else if(flag == 1) {
                pageable.fixCurPage(nextPageNum);
                pageable.of(nextPageNum, pageStartCartItem, pageLastCartItem);
            } else if(flag == 2) {
                pageable.fixCurPage(curPageNum);
                pageable.of(curPageNum, pageStartCartItem, pageLastCartItem);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
    }
}