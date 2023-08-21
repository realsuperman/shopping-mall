package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemServiceTest{
    private ItemService itemService;

    public ItemServiceTest() {
        this.itemService = new ItemService(new ItemDao());
    }

    @Test
    void selectAll(){
        List<Item> items = itemService.selectAll();
        System.out.println(items);
    }

    @Test
    void selectCategoryBest(){
        long masterCategoryId = 1L;
        List<categoryBestResponse> res = itemService.selectCategoryBest(masterCategoryId);
        System.out.println(res);
    }

    @Test
    void selectCategoryRecent(){
        long categoryId = 31L;
        List<categoryRecentResponse> res = itemService.selectCategoryRecent(categoryId);
        System.out.println(res);
    }

}