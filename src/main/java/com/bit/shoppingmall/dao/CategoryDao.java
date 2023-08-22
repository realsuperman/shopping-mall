package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryDao {
    public List<Category> selectAll(SqlSession session){
        return session.selectList("category.selectAll");
    }

    public Category selectCategoryById(SqlSession session, long categoryId){
        return session.selectOne("category.findById",categoryId);
    }

}
