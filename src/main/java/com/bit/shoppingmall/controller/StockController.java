package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.service.CargoService;
import com.bit.shoppingmall.service.ItemService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StockController extends HttpServlet {
    private final CargoService cargoService;

    public StockController(CargoService cargoService) {
        this.cargoService=cargoService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("count",cargoService.getCountStock(request.getParameter("itemName")));
            jsonObject.put("key",cargoService.selectStock(Long.valueOf(request.getParameter("page")), request.getParameter("itemName")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }
}
