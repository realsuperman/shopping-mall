package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;

import java.util.List;

public class ItemService {
    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<categoryBestResponse> selectCategoryBest(long masterCategoryId){
        return itemDao.selectCategoryBest(GetSessionFactory.getInstance().openSession(), masterCategoryId);
    }
    public List<categoryRecentResponse> selectCategoryRecent(long categoryId){
        return itemDao.selectCategoryRecent(GetSessionFactory.getInstance().openSession(), categoryId);
    }

    public Item selectItemById(long itemId){
        return itemDao.selectItemById(GetSessionFactory.getInstance().openSession(), itemId);
    }

}
