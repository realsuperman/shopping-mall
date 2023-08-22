package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.service.ItemService;
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
        JSONObject jsonObject = new JSONObject();
        try{
            List<categoryBestResponse> categoryBestResponses = itemService.selectCategoryBest(categoryId);
            jsonObject.put("key",categoryBestResponses);
        }catch (Exception e){
            throw new RuntimeException();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

}
