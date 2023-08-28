package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CategoryService;
import com.bit.shoppingmall.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController extends HttpServlet {
    private final ItemService itemService;
//    private final String fileName = "home";
    private final String fileName = "mainPage";
    private final Integer EXPOSE_CATEGORY_CNT = 3;
    private final Long EXPOSE_ONLY_FOUR_DATA = null;

    public HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long page = EXPOSE_ONLY_FOUR_DATA;
        List<Category> categories = itemService.selectLeafCategories();
        List<Long> leafCategories = categories.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());
        Collections.shuffle(leafCategories);

        List<List<categoryRecentResponse>> items = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        List<String> categoryNames = categories.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());

        for (int i = 0; i < leafCategories.size(); i++){
            long categoryId = leafCategories.get(i);
            List<categoryRecentResponse> categoryRecentResponses = itemService.selectCategoryRecent(page, categoryId);
            if(categoryRecentResponses.size() >= 4){
                categoryIds.add(categoryId);
                items.add(categoryRecentResponses);
                if(items.size() >= EXPOSE_CATEGORY_CNT) break;
            }
        }
        request.setAttribute("itemList",items);
        request.setAttribute("categoryIds",categoryIds);
        request.setAttribute("categoryNames",categoryNames);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }

}
