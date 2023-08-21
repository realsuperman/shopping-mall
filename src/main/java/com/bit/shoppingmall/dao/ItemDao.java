package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ItemDao {
    public List<Item> selectAll(SqlSession session){
        return session.selectList("item.selectAll");
    }

    public List<categoryBestResponse> selectCategoryBest(SqlSession session, long masterCategoryId){
        return session.selectList("item.selectCategoryBest",masterCategoryId);
    }


}
