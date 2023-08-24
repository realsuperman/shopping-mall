package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.CartItem;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.domain.Membership;
import com.bit.shoppingmall.dto.CartItemDto;
import com.bit.shoppingmall.exception.NoSuchDataException;
import com.bit.shoppingmall.exception.NotContainedAnything;
import com.bit.shoppingmall.global.LabelFormat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CartController extends HttpServlet {
    private Logger cart_log = Logger.getLogger("cart");
    private final CartService cartService;
    private final ItemService itemService;
    private final String fileName = "cart";

    public CartController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Consumer consumer = (Consumer) request.getSession().getAttribute("login_user");
        long loginedId = consumer.getConsumerId();

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

            response.setCharacterEncoding("UTF-8");
            request.setAttribute("cartItems", foundItems);
        } catch (NotContainedAnything e) {
            //에러 처리
            cart_log.info(e.getMessage());
            request.setAttribute("errMsg", e.getMessage());

            // 에러를 처리하는 페이지로 포워드
            RequestDispatcher dispatcher = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
            dispatcher.forward(request, response);
        }
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cart_log.info("CartController doPost...");
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
            CartRestController controller = new CartRestController(cartService, itemService);
//            controller.doGet(request, response);
            response.sendRedirect("/cart");


        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {//에러처리 추후 수정
            cart_log.info(e.getMessage());
        }
    }
}