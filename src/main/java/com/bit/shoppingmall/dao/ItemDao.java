package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ItemDao {
    public int insertItem(SqlSession session, Item item) {
        return session.insert("item.insertItem",item);
    }

    public Item selectByKey(SqlSession session, long itemId){
        return session.selectOne("item.selectItemByKey",itemId);
    }
}
