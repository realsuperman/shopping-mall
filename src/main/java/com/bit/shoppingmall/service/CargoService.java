package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dto.CargoDto;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import com.bit.shoppingmall.dto.StockStatDto;
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
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.selectStock(sqlSession, setStockSearchDto(page,itemName));
        }
    }

    public int getCountStock(String itemName) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.getCountStock(sqlSession, setStockSearchDto(null,itemName));
        }
    }

    public List<CargoDto> selectStockStat(Long page, String itemName) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.selectStockStat(sqlSession, setStockSearchDto(page,itemName));
        }
    }

    public int getCountStockStat(String itemName) {
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return cargoDao.getCountStockStat(sqlSession, setStockSearchDto(null,itemName));
        }
    }

    public void updateStockStat(List<StockStatDto> stockStatDtoList) {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession(false);
        try {
            for(StockStatDto stockStatDto : stockStatDtoList){
                if(cargoDao.updateCargoStat(sqlSession, stockStatDto) == 0){ // update 예외가 아닌 update 자체를 찾을 수 없는 상황
                    throw new NullPointerException(); // 업데이트 안된 케이스면 예외를 던진다 TODO 이게 필요한가?
                }
            }
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    private StockSearchDto setStockSearchDto(Long page, String itemName){
        StockSearchDto stockSearchDto = new StockSearchDto();
        stockSearchDto.setItemName('%'+itemName+'%');
        stockSearchDto.setPageSize(PageSize.SIZE.size());
        long offset = page==null?0:page*16;
        stockSearchDto.setOffset(offset);
        return stockSearchDto;
    }
}