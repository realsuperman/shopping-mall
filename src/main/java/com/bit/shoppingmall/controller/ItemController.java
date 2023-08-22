package com.bit.shoppingmall.controller;

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


    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Long page = Long.parseLong(request.getParameter("page"));
        request.setAttribute("items",itemService.selectCategoryRecent(page,categoryId));
        request.setAttribute("categoryId",categoryId);
        request.setAttribute("nowPage",page);
        request.setAttribute("lastPage",Math.ceil(1d * itemService.itemCount(categoryId)/ONE_PAGE_ITEM_CNT));
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

}
