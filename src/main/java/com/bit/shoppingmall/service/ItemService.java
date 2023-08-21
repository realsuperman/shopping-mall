package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.global.GetSessionFactory;

import java.util.List;

public class ItemService {
    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> selectAll(){
        return itemDao.selectAll(GetSessionFactory.getInstance().openSession());
    }

    public List<categoryBestResponse> selectCategoryBest(long masterCategoryId){
        return itemDao.selectCategoryBest(GetSessionFactory.getInstance().openSession(), masterCategoryId);
    }
}
