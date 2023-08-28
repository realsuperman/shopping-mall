package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryDao {
    public List<Category> selectAll(SqlSession session){
        return session.selectList("category.findCategories");
    }

    public Category findCategoryById(SqlSession session, Long categoryId){
        return session.selectOne("category.findCategories",categoryId);
    }

    public List<Category> findParentsById(SqlSession session, long categoryId){
        return session.selectList("category.findParents",categoryId);
    }

}
