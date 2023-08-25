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
        if(request.getSession().getAttribute("checkedIdSet") != null) {
            Set<Long> set = (Set<Long>)request.getSession().getAttribute("checkedIdSet");
            request.setAttribute("checkedIdSet", set);
            System.out.println("checkedIdSet: " + set);
        }
        Consumer loginedUser = (Consumer)request.getSession().getAttribute("login_user");
        long sessionId = loginedUser.getConsumerId();
        if(pageable == null) {
            pageable = new Pageable();
            pageable.of(pageable.getCurPage(), sessionId);
        }
        int start = pageable.getPageStartCartItem();
        int end = pageable.getPageLastCartItem();
        cart_log.info("start: " + start);
        cart_log.info("end: " + end);

        List<CartItem> cartItems = cartService.getLimit5(sessionId, start, end);
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
        //cart_log.info("cartItemDtos: " + cartItemDtos);
        cart_log.info("pageable curPage: " + pageable.getCurPage());
        cart_log.info("pageable blockStartNum: " + pageable.getBlockStartNum());
        cart_log.info("pageable blockLastNum: " + pageable.getBlockLastNum());
        request.setAttribute("pageable", pageable);
        request.setAttribute("cartItems", cartItemDtos);
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
            cart_log.info("checkedId: " + checkedId);

            Set<Long> set;
            if(request.getSession().getAttribute("checkedIdSet") != null) {
                set = (Set<Long>)request.getSession().getAttribute("checkedIdSet");
                set.add(checkedId);
            } else {
                set = new HashSet<>();
                set.add(checkedId);
            }
            request.getSession().setAttribute("checkedIdSet", set);
        } else {
            Consumer loginedUser = (Consumer) request.getSession().getAttribute("login_user");
            long sessionId = loginedUser.getConsumerId();
            try {
                StringBuilder requestBody = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                jsonData = new JSONObject(requestBody.toString());
                Integer curPageNum = Integer.parseInt(jsonData.getString("curPageNum"));
                Integer prevPageNum = Integer.parseInt(jsonData.getString("prevPageNum"));
                Integer nextPageNum = Integer.parseInt(jsonData.getString("nextPageNum"));
                Integer pageStartCartItem = Integer.parseInt(jsonData.getString("pageStartCartItem"));
                Integer pageLastCartItem = Integer.parseInt(jsonData.getString("pageLastCartItem"));
                Integer flag = jsonData.getInt("flag");

                cart_log.info("curPageNum: " + curPageNum);
                cart_log.info("prevPageNum: " + prevPageNum);
                cart_log.info("nextPageNum: " + nextPageNum);
                cart_log.info("pageStartCartItem: " + pageStartCartItem);
                cart_log.info("pageLastCartItem: " + pageLastCartItem);

                pageable = new Pageable();
                if (flag == 0) {
                    pageable.fixCurPage(prevPageNum);
                    pageable.of(prevPageNum, sessionId);
                } else {
                    pageable.fixCurPage(nextPageNum);
                    pageable.of(nextPageNum, sessionId);
                }
                pageable.fixPageStartCartItem(pageStartCartItem);
                pageable.fixPageLastCartItem(pageLastCartItem);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {//에러처리 추후 수정
                cart_log.info(e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            JSONObject jsonData = new JSONObject(requestBody.toString());
            Long excludedId = Long.parseLong(jsonData.getString("itemId"));
            request.setAttribute("excludedId", excludedId);

            RequestDispatcher rd = request.getRequestDispatcher("/cart-ajax");
            rd.forward(request, response);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
    }
}