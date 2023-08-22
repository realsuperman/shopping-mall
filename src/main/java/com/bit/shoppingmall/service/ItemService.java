package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService {
    private final ItemDao itemDao;
    private final Long ONE_PAGE_ITEM_CNT = 16L;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<categoryBestResponse> selectCategoryBest(long masterCategoryId){
        return itemDao.selectCategoryBest(GetSessionFactory.getInstance().openSession(), masterCategoryId);
    }
    public List<categoryRecentResponse> selectCategoryRecent(Long page, Long categoryId){
        Map<String, Long> map = new HashMap<>();
        map.put("limit", ONE_PAGE_ITEM_CNT);
        if(page == null){
            map.put("offset",null);
        }else{
            map.put("offset",(page-1) * ONE_PAGE_ITEM_CNT);
        }
        map.put("category_id", categoryId);
        System.out.println("map = " + map);
        return itemDao.selectCategoryRecent(GetSessionFactory.getInstance().openSession(), map);
    }

    public int itemCount(Long categoryId){
        return itemDao.getItemCount(GetSessionFactory.getInstance().openSession(), categoryId);
    }

    public Item selectItemById(long itemId){
        return itemDao.selectItemById(GetSessionFactory.getInstance().openSession(), itemId);
    }

}
