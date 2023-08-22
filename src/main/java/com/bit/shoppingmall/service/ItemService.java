package com.bit.shoppingmall.service;


import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService {
    private final ItemDao itemDao;

    private final Long ONE_PAGE_ITEM_CNT = 16L;
    private final CargoDao cargoDao;

    public ItemService(ItemDao itemDao, CargoDao cargoDao) {
        this.itemDao = itemDao;
        this.cargoDao = cargoDao;
    }

    public List<categoryBestResponse> selectCategoryBest(long masterCategoryId) {
        return itemDao.selectCategoryBest(GetSessionFactory.getInstance().openSession(), masterCategoryId);
    }

    public List<categoryRecentResponse> selectCategoryRecent(Long page, Long categoryId) {
        Map<String, Long> map = new HashMap<>();
        map.put("limit", ONE_PAGE_ITEM_CNT);
        if (page == null) {
            map.put("offset", null);
        } else {
            map.put("offset", (page - 1) * ONE_PAGE_ITEM_CNT);
        }
        map.put("category_id", categoryId);
        return itemDao.selectCategoryRecent(GetSessionFactory.getInstance().openSession(), map);
    }

    public int itemCount(Long categoryId) {
        return itemDao.getItemCount(GetSessionFactory.getInstance().openSession(), categoryId);
    }

    public Item selectItemById(long itemId) {
        return itemDao.selectItemById(GetSessionFactory.getInstance().openSession(), itemId);
    }

    public void insertItem(Item item, int count) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession(false);
        try {
            itemDao.insertItem(sqlSession, item);
            List<Cargo> cargoList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                cargoList.add(Cargo.builder().itemId(item.getItemId()).statusId(3L).build());
            }
            cargoDao.insertCargo(sqlSession, cargoList);

            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public List<StockDto> selectAll(Long page, String itemName) {
        StockSearchDto stockSearchDto = new StockSearchDto();
        stockSearchDto.setItemName(itemName);
        stockSearchDto.setPageSize(ONE_PAGE_ITEM_CNT);
        long offset = page==null?0:page*16;
        stockSearchDto.setOffset(offset);
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectStock(sqlSession, stockSearchDto);
        }
    }
}
