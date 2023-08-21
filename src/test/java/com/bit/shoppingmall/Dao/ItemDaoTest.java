package com.bit.shoppingmall.Dao;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemDaoTest extends RootTest {
    ItemDao itemDao = new ItemDao();
    @Test
    public void insertTest(){
        Item item = Item.builder().
                categoryId(10L).
                itemName("csh").
                itemPrice(1000L).
                itemDescription("csh").
                itemImagePath("test;test;test").
                build();
        assertEquals(1, itemDao.insertItem(sqlSession,item));
    }
}
