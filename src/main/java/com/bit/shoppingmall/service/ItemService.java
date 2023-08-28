package com.bit.shoppingmall.service;


import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Category;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.dto.RecentCategoryDto;
import com.bit.shoppingmall.dto.CategoryBestResponse;
import com.bit.shoppingmall.dto.CategoryRecentResponse;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.PageSize;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final ItemDao itemDao;
    private final CargoDao cargoDao;

    public ItemService(ItemDao itemDao, CargoDao cargoDao) {
        this.itemDao = itemDao;
        this.cargoDao = cargoDao;
    }

    public List<CategoryBestResponse> selectCategoryBest(long masterCategoryId) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectCategoryBest(sqlSession, masterCategoryId);
        }
    }

    public List<CategoryRecentResponse> selectCategoryRecent(Long page, Long categoryId) {
        RecentCategoryDto recentCategoryDto = RecentCategoryDto.builder()
                .limit(PageSize.SIZE.size())
                .categoryId(categoryId)
                .offset((page == null) ? null : (page - 1) * PageSize.SIZE.size())
                .build();
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            List<CategoryRecentResponse> categoryRecentRespons = itemDao.selectCategoryRecent(sqlSession, recentCategoryDto);
            return categoryRecentRespons;
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

    public List<Category> selectLeafCategories(){
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return itemDao.selectLeafCategories(sqlSession);
        }
    }
}