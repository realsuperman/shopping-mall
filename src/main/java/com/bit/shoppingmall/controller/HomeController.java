package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.dto.CategoryRecentResponse;
import com.bit.shoppingmall.global.LabelFormat;
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
        List<Category> leafCategories = itemService.selectLeafCategories();
        Collections.shuffle(leafCategories);
        List<Long> leafCategoryIds = leafCategories.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());

        List<List<CategoryRecentResponse>> items = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>();

        // TODO: IN을 활용한 쿼리로 변경할 수 있을지 고민해보기 (ex - SELECT * FROM item WHERE category_id IN (15,16,17,18) ORDER BY item_register_time DESC LIMIT 4);
        for (int i = 0; i < leafCategoryIds.size(); i++){
            long categoryId = leafCategoryIds.get(i);
            List<CategoryRecentResponse> categoryRecentResponse = itemService.selectCategoryRecent(page, categoryId);
            if(categoryRecentResponse.size() >= 4){
                categoryNames.add(leafCategories.get(i).getCategoryName());
                categoryIds.add(categoryId);
                items.add(categoryRecentResponse);
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
