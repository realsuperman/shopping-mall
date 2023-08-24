package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryDao {
    public List<Category> selectAll(SqlSession session){
        return session.selectList("category.selectAll");
    }

    public Category findCategoryById(SqlSession session, long categoryId){
        return session.selectOne("category.findById",categoryId);
    }

    public List<Category> findParentsById(SqlSession session, long categoryId){
        return session.selectList("category.findParents",categoryId);
    }

}
