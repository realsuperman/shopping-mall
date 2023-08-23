package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.PageSize;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CargoService {
    private final CargoDao cargoDao;

    public CargoService(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    public List<StockDto> selectStock(Long page, String itemName) {
        StockSearchDto stockSearchDto = new StockSearchDto();
        stockSearchDto.setItemName('%'+itemName+'%');
        stockSearchDto.setPageSize(PageSize.SIZE.size());
        long offset = page==null?0:page*16;
        stockSearchDto.setOffset(offset);
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.selectStock(sqlSession, stockSearchDto);
        }
    }

    public int getCountStock(String itemName) {
        StockSearchDto stockSearchDto = new StockSearchDto();
        stockSearchDto.setItemName('%'+itemName+'%');
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.getCountStock(sqlSession, stockSearchDto);
        }
    }
}