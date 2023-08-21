package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.service.ItemService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ItemController extends HttpServlet {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService=itemService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        StringBuilder imageName = new StringBuilder();
        imageName.append(request.getParameter("image1-name")); imageName.append(";");
        imageName.append(request.getParameter("image2-name")); imageName.append(";");
        imageName.append(request.getParameter("image3-name")); imageName.append(";");
        imageName.append(request.getParameter("image4-name")); imageName.append(";");
        imageName.append(request.getParameter("image5-name")); imageName.append(";");
        imageName.append(request.getParameter("image6-name"));
        System.out.println(request);

        Item item = Item.builder()
                .categoryId(Long.valueOf(request.getParameter("detailCategory")))
                .itemName(request.getParameter("item_name"))
                .itemPrice(Long.valueOf(request.getParameter("item_price")))
                .itemDescription(request.getParameter("item_desc"))
                .itemImagePath(imageName.toString())
                .build();
        itemService.insertItem(item, Integer.parseInt(request.getParameter("item_quantity")));
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
