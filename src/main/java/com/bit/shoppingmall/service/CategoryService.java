package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CategoryDao;
import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> selectAll(){
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return categoryDao.selectAll(sqlSession);
        }
    }

    public Category findCategoryById(long categoryId){
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()){
            return categoryDao.findCategoryById(sqlSession,categoryId);
        }
    }

    public List<Category> findParentsById(long categoryId){
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()){
            return categoryDao.findParentsById(sqlSession,categoryId);
        }
    }

}