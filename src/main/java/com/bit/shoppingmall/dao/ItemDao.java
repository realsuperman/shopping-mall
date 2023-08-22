package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.StockDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class ItemDao {
    public int insertItem(SqlSession session, Item item) {
        return session.insert("item.insertItem",item);
    }

    public Item selectByKey(SqlSession session, long itemId){
        return session.selectOne("item.selectItemByKey",itemId);
    }

    public List<StockDto> selectAll(SqlSession session, Map<String, String> map){
        return session.selectList("item.selectStock",map);
    }
    public List<categoryBestResponse> selectCategoryBest(SqlSession session, long masterCategoryId){
        return session.selectList("item.selectCategoryBest",masterCategoryId);
    }

    public List<categoryRecentResponse> selectCategoryRecent(SqlSession session, long categoryId){
        return session.selectList("item.selectCategoryRecent",categoryId);
    }

    public Item selectItemById(SqlSession session, long itemId){
        return session.selectOne("item.findById",itemId);
    }

}
