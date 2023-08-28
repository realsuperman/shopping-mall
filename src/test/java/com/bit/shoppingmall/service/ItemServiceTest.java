package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.CategoryBestResponse;
import com.bit.shoppingmall.dto.CategoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

class ItemServiceTest{
    private ItemService itemService;

    ItemDao itemDao = new ItemDao();
    CargoDao cargoDao = new CargoDao();
    SqlSession sqlSession = GetSessionFactory.getInstance().openSession(false);

    public ItemServiceTest() {
        this.itemService = new ItemService(new ItemDao(),new CargoDao());
    }

    @Test
    void selectCategoryBest(){
        long masterCategoryId = 1L;
        List<CategoryBestResponse> res = itemService.selectCategoryBest(masterCategoryId);
        System.out.println(res);
    }

    @DisplayName("해당 카테고리의 상품을 페이징처리했을 때 page*16번째부터 최대 16개의 데이터를 반환합니다.")
    @Test
    void selectCategoryRecent(){
        Long categoryId = 31L;
        Long page = 1L;
        List<CategoryRecentResponse> res = itemService.selectCategoryRecent(page,categoryId);
        System.out.println(res);
    }

    @DisplayName("page에 null값이 들어오면 페이징 처리를 진행하지 않고 최대 4개의 값만 반환합니다.")
    @Test
    void selectCategoryRecent2(){
        Long categoryId = 31L;
        Long page = null;
        List<CategoryRecentResponse> res = itemService.selectCategoryRecent(page,categoryId);
        System.out.println(res);
    }

    @Test
    void selectItemById(){
        long itemId = 31;
        Item item = itemService.selectItemById(itemId);
        System.out.println(item);
    }

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
        sqlSession = GetSessionFactory.getInstance().openSession();
        Item item = itemDao.selectByKey(sqlSession, itemId);
        assertNull(item);
    }
}
