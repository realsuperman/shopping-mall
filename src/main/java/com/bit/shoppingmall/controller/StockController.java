package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dto.StockStatDto;
import com.bit.shoppingmall.exception.PageNotFoundException;
import com.bit.shoppingmall.service.CargoService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockController extends HttpServlet {
    private final CargoService cargoService;

    public StockController(CargoService cargoService) {
        this.cargoService=cargoService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();

        Object count;
        Object key;
        if("/stat".equals(request.getParameter("url"))){
            count = cargoService.getCountStockStat(request.getParameter("itemName"));
            key = cargoService.selectStockStat(Long.valueOf(request.getParameter("page")), request.getParameter("itemName"));
        }else if("/stock".equals(request.getParameter("url"))){
            count = cargoService.getCountStock(request.getParameter("itemName"));
            key = cargoService.selectStock(Long.valueOf(request.getParameter("page")), request.getParameter("itemName"));
        }else{
            throw new PageNotFoundException();
        }

        try {
            jsonObject.put("count",count);
            jsonObject.put("key",key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payload = request.getReader().lines().collect(Collectors.joining());
        List<StockStatDto> stockStatDtoList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(payload);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cargoStatusObj = jsonArray.getJSONObject(i);
                stockStatDtoList.add(StockStatDto.builder().cargoId(cargoStatusObj.getLong("cargoId"))
                        .statusId(cargoStatusObj.getLong("status")).build());
            }
        } catch (Exception e) { // 파싱 실패
            throw new RuntimeException(); // TODO JSON 관련 예외 클래스 만들고 똑같이 던져야 할 듯?
        }

        cargoService.updateStockStat(stockStatDtoList);
    }
}
