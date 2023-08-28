package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.CategoryBestResponse;
import com.bit.shoppingmall.service.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ItemJsonController extends HttpServlet {
    private final ItemService itemService;

    public ItemJsonController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long categoryId = Long.parseLong(request.getParameter("categoryId"));
        JSONArray jsonArray = new JSONArray();
        try{
            List<CategoryBestResponse> categoryBestResponse = itemService.selectCategoryBest(categoryId);
            for (int i = 0; i < categoryBestResponse.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("itemId", categoryBestResponse.get(i).getItemId());
                jsonObject.put("categoryId", categoryBestResponse.get(i).getCategoryId());
                jsonObject.put("itemName", categoryBestResponse.get(i).getItemName());
                jsonObject.put("itemPrice", categoryBestResponse.get(i).getItemPrice());
                jsonObject.put("itemDescription", categoryBestResponse.get(i).getItemDescription());
                jsonObject.put("itemImagePath", categoryBestResponse.get(i).getItemImagePath());
                jsonObject.put("itemRegisterTime", categoryBestResponse.get(i).getItemRegisterTime());
                jsonObject.put("cnt", categoryBestResponse.get(i).getCnt());

                jsonArray.put(jsonObject);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(jsonArray);
    }

}
