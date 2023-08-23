package com.bit.shoppingmall.Dao;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemDaoTest extends RootTest {
    ItemDao itemDao = new ItemDao();
    CargoDao cargoDao = new CargoDao();

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
        List<StockDto> stockDtoList = cargoDao.selectStock(sqlSession,new StockSearchDto());

        int sum = 0;
        for(StockDto sto: stockDtoList){
            sum+=sto.getCnt();
        }
        System.out.println(sum);

        stockDtoList = cargoDao.selectStock(sqlSession,null);
        sum = 0;
        for(StockDto sto: stockDtoList){
            sum+=sto.getCnt();
        }
        System.out.println(sum);
    }
}
