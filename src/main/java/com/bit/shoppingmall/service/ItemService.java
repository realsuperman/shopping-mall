package com.bit.shoppingmall.service;


import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.categoryBestResponse;
import com.bit.shoppingmall.dto.categoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.PageSize;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService {
    private final ItemDao itemDao;
    private final CargoDao cargoDao;

    public ItemService(ItemDao itemDao, CargoDao cargoDao) {
        this.itemDao = itemDao;
        this.cargoDao = cargoDao;
    }

    public List<categoryBestResponse> selectCategoryBest(long masterCategoryId) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectCategoryBest(sqlSession, masterCategoryId);
        }
    }

    public List<categoryRecentResponse> selectCategoryRecent(Long page, Long categoryId) {
        Map<String, Long> map = new HashMap<>();
        map.put("limit", PageSize.SIZE.size()); // TODO CargoService 참고
        if (page == null) {
            map.put("offset", null);
        } else {
            map.put("offset", (page - 1) * PageSize.SIZE.size());
        }
        map.put("category_id", categoryId);
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectCategoryRecent(sqlSession, map);
        }
    }

    public int itemCount(Long categoryId) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.getItemCount(sqlSession, categoryId);
        }
    }

    public Item selectItemById(long itemId) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectItemById(sqlSession, itemId);
        }
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
}