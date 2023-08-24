package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.service.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
            List<categoryBestResponse> categoryBestResponses = itemService.selectCategoryBest(categoryId);
            for (int i = 0; i < categoryBestResponses.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("itemId",categoryBestResponses.get(i).getItemId());
                jsonObject.put("categoryId",categoryBestResponses.get(i).getCategoryId());
                jsonObject.put("itemName",categoryBestResponses.get(i).getItemName());
                jsonObject.put("itemPrice",categoryBestResponses.get(i).getItemPrice());
                jsonObject.put("itemDescription",categoryBestResponses.get(i).getItemDescription());
                jsonObject.put("itemImagePath",categoryBestResponses.get(i).getItemImagePath());
                jsonObject.put("itemRegisterTime",categoryBestResponses.get(i).getItemRegisterTime());
                jsonObject.put("cnt",categoryBestResponses.get(i).getCnt());

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
