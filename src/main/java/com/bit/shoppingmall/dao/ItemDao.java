package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ItemDao {
    public List<categoryBestResponse> selectCategoryBest(SqlSession session, long masterCategoryId){
        return session.selectList("item.selectCategoryBest",masterCategoryId);
    }

    public List<categoryRecentResponse> selectCategoryRecent(SqlSession session, long categoryId){
        return session.selectList("item.selectCategoryRecent",categoryId);
    }


}
