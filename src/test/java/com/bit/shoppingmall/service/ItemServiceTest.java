package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemServiceTest{
    private ItemService itemService;

    public ItemServiceTest() {
        this.itemService = new ItemService(new ItemDao());
    }

    @Test
    void selectCategoryBest(){
        long masterCategoryId = 1L;
        List<categoryBestResponse> res = itemService.selectCategoryBest(masterCategoryId);
        System.out.println(res);
    }

    @DisplayName("�ش� ī�װ��� ��ǰ�� ����¡ó������ �� page��° �����͸� ��ȯ�մϴ�.")
    @Test
    void selectCategoryRecent(){
        Long categoryId = 31L;
        Long page = 1L;
        List<categoryRecentResponse> res = itemService.selectCategoryRecent(page,categoryId);
        System.out.println(res);
    }

    @DisplayName("page�� null���� ���� ����¡ ó���� �������� �ʰ� �ִ� 4���� ���� ��ȯ�մϴ�.")
    @Test
    void selectCategoryRecent2(){
        Long categoryId = 31L;
        Long page = null;
        List<categoryRecentResponse> res = itemService.selectCategoryRecent(page,categoryId);
        System.out.println(res);
    }

    @Test
    void selectItemById(){
        long itemId = 31;
        Item item = itemService.selectItemById(itemId);
        System.out.println(item);
    }

}