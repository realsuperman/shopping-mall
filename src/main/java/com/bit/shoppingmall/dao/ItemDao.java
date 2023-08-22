package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDao {
    public List<categoryBestResponse> selectCategoryBest(SqlSession session, long masterCategoryId){
        return session.selectList("item.selectCategoryBest",masterCategoryId);
    }

    public List<categoryRecentResponse> selectCategoryRecent(SqlSession session, Map<String, Long> map){
        return session.selectList("item.selectCategoryRecent",map);
    }

    public Item selectItemById(SqlSession session, long itemId){
        return session.selectOne("item.findById",itemId);
    }

    public int getItemCount(SqlSession session, long categoryId){
        return session.selectOne("item.count",categoryId);
    }


}
