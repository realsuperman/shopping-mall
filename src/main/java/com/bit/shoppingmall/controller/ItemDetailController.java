package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CategoryService;
import com.bit.shoppingmall.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDetailController extends HttpServlet {
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final String fileName = "itemDetail";


    public ItemDetailController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long itemId = Long.parseLong(request.getParameter("itemId"));
        Item item = itemService.selectItemById(itemId);

        long categoryId = item.getCategoryId();
        List<Category> parentsById = categoryService.findParentsById(categoryId);
        List<String> upperCategoryNames = parentsById.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
        Collections.reverse(upperCategoryNames);
        request.setAttribute("item",item);
        request.setAttribute("upperCategoryNames", upperCategoryNames);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
