package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ItemDao {
    public List<CategoryBestResponse> selectCategoryBest(SqlSession session, long masterCategoryId){
        return session.selectList("item.selectCategoryBest",masterCategoryId);
    }

    public List<CategoryRecentResponse> selectCategoryRecent(SqlSession session, RecentCategoryDto recentCategoryDto){
        return session.selectList("item.selectCategoryRecent",recentCategoryDto);
    }

    public Item selectItemById(SqlSession session, long itemId){
        return session.selectOne("item.findById",itemId);
    }

    public int insertItem(SqlSession session, Item item) {
        return session.insert("item.insertItem",item);
    }

    public int getItemCount(SqlSession session, long categoryId){
        return session.selectOne("item.count",categoryId);
    }

    public Item selectByKey(SqlSession session, long itemId){
        return session.selectOne("item.selectItemByKey",itemId);
    }
    public List<Category> selectLeafCategories(SqlSession session){
        return session.selectList("item.selectLeafCategories");
    }

}
