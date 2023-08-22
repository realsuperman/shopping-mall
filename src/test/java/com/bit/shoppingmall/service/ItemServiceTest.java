package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Item;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bit.shoppingmall.global.GetSessionFactory.getInstance;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItemServiceTest{
    ItemDao itemDao = new ItemDao();
    CargoDao cargoDao = new CargoDao();
    SqlSession sqlSession = getInstance().openSession(false);

    private ItemService itemService;

    public ItemServiceTest() {
        this.itemService = new ItemService(new ItemDao());
    
    
    @Test
    public void insertItemTest(){
        long itemId = 0;
        try {
            Item item = Item.builder().
                    categoryId(10L).
                    itemName("csh").
                    itemPrice(1000L).
                    itemDescription("csh").
                    itemImagePath("test;test;test").
                    build();

            List<Cargo> cargoList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                cargoList.add(Cargo.builder().statusId(3L).itemId(33L).build());
            }

            itemDao.insertItem(sqlSession, item);
            itemId = item.getItemId();
            cargoDao.insertCargo(sqlSession, cargoList);

            if(1==1) throw new Exception();
        }catch (Exception e){
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
        sqlSession = getInstance().openSession();
        Item item = itemDao.selectByKey(sqlSession, itemId);
        assertNull(item);
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

    @Test
    void selectItemById(){
        long itemId = 31;
        Item item = itemService.selectItemById(itemId);
        System.out.println(item);
    }

}
