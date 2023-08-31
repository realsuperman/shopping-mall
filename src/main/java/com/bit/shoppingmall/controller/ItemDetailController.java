package com.bit.shoppingmall.controller;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.LabelFormat;
import com.bit.shoppingmall.service.CategoryService;
import com.bit.shoppingmall.service.ItemService;
import org.apache.ibatis.session.SqlSession;

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
    private final CargoDao cargoDao;
    private final String fileName = "itemDetail";


    public ItemDetailController(ItemService itemService, CategoryService categoryService, CargoDao cargoDao) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.cargoDao = cargoDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long itemId = Long.parseLong(request.getParameter("itemId"));
        Item item = itemService.selectItemById(itemId);

        String sucMsg = String.valueOf(request.getParameter("sucMsg"));
        long categoryId = item.getCategoryId();
        List<Category> parentsById = categoryService.findParentsById(categoryId);
        List<String> upperCategoryNames = parentsById.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
        int cargoCnt = cargoDao.cargoCnt(GetSessionFactory.getInstance().openSession(),itemId);
        Collections.reverse(upperCategoryNames);
        request.setAttribute("item",item);
        request.setAttribute("upperCategoryNames", upperCategoryNames);
        request.setAttribute("cargoCnt",cargoCnt);
        request.setAttribute("sucMsg",sucMsg);

        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label() + fileName + LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
