package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.service.CategoryService;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryController extends HttpServlet {
    private final CategoryService categoryService;
    private JSONObject jsonObject = null;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(jsonObject==null){
            List<Category> categories = categoryService.selectAll();
            Map<String, List<String>> largeCategory = new LinkedHashMap<>();
            Map<String, List<String>> middleCategory = new LinkedHashMap<>();
            initCategory(categories, largeCategory, middleCategory);

            jsonObject = new JSONObject();
            try {
                jsonObject.put("largeCategory", largeCategory);
                jsonObject.put("middleCategory", middleCategory);
            }catch(Exception e){ // TODO 무슨 예외를 던질가
                throw new RuntimeException();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

    private void initCategory(List<Category> categories, Map<String, List<String>> largeCategory,
                              Map<String, List<String>> middleCategory){
        for(Category category : categories){
            if(category.getMasterCategoryId()==null){
                largeCategory.put(category.getCategoryId()+";"+category.getCategoryName(),new ArrayList<>());
            }
        }

        setCategory(categories, largeCategory, middleCategory);
        setCategory(categories, middleCategory, null);

    }

    private void setCategory(List<Category> categories, Map<String, List<String>> categoryMap,
                             Map<String, List<String>> nextCategoryMap) {
        for (String categoryKey : categoryMap.keySet()) {
            String[] key = categoryKey.split(";");
            for (Category category : categories) {
                if(category.getMasterCategoryId()==null) continue;
                if (Long.parseLong(key[0]) == category.getMasterCategoryId()) { // 타입 변환 후 비교
                    List<String> list = categoryMap.get(categoryKey);
                    list.add(category.getCategoryId() + ";" + category.getCategoryName());
                    if(nextCategoryMap!=null){
                        nextCategoryMap.put(category.getCategoryId() + ";" + category.getCategoryName(), new ArrayList<>());
                    }
                }
            }
        }
    }
}