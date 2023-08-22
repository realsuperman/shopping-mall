package com.bit.shoppingmall.Dao;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.StockDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // select의 경우 예외만 발생안하면 테스트 통과로 가정
    @Test
    void selectAll() {
        Map<String, String> map = new HashMap<>();
        map.put("itemName","%사과%");
        map.put("pageSize","");
        map.put("offset","");
        List<StockDto> stockDtoList = itemDao.selectAll(sqlSession,map);

        int sum = 0;
        for(StockDto sto: stockDtoList){
            sum+=sto.getCnt();
        }
        System.out.println(sum);

        stockDtoList = itemDao.selectAll(sqlSession,null);
        sum = 0;
        for(StockDto sto: stockDtoList){
            sum+=sto.getCnt();
        }
        System.out.println(sum);
    }
}
