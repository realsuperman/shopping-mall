package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CategoryDao;
import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.global.GetSessionFactory;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> selectAll(){
        return categoryDao.selectAll(GetSessionFactory.getInstance().openSession());
    }

}