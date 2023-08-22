package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ItemController extends HttpServlet {
    private final ItemService itemService;
    private final String fileName = "item";
    private final Long ONE_PAGE_ITEM_CNT = 16L;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder imageName = new StringBuilder();
        imageName.append(request.getParameter("image1-name"));
        imageName.append(";");
        imageName.append(request.getParameter("image2-name"));
        imageName.append(";");
        imageName.append(request.getParameter("image3-name"));
        imageName.append(";");
        imageName.append(request.getParameter("image4-name"));
        imageName.append(";");
        imageName.append(request.getParameter("image5-name"));
        imageName.append(";");
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Long page = Long.parseLong(request.getParameter("page"));
        request.setAttribute("items", itemService.selectCategoryRecent(page, categoryId));
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("nowPage", page);
        request.setAttribute("lastPage", Math.ceil(1d * itemService.itemCount(categoryId) / ONE_PAGE_ITEM_CNT));
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
