package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.dto.CargoDto;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import com.bit.shoppingmall.dto.StockStatDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;


public class CargoDao {
    public List<Cargo> selectAll(SqlSession session){
        return session.selectList("cargo.selectAll");
    }
    public int insertCargo(SqlSession session, List<Cargo> cargoList) {
        return session.insert("cargo.insertCargo",cargoList);
    }

    public int updateCargoStatusByCargoId(SqlSession sqlSession, Map<String, Long> map) {
        return sqlSession.update("cargo.updateCargoStatusByCargoId", map);
    }

    public long selectCountByItemId(SqlSession sqlSession, Long itemId) {
        return sqlSession.selectOne("cargo.selectCountByItemId", itemId);
    }

    public List<Cargo> selectCargoToDeliver(SqlSession sqlSession, Map<String, Long> map) {
        return sqlSession.selectList("cargo.selectCargoToDeliver", map);
    }

    public List<Cargo> selectCargoByItemIdAndNotStatusId(SqlSession sqlSession, Map<String, Long> map) {
        return sqlSession.selectList("cargo.selectCargoByItemIdAndNotStatusId", map);
    }
    public int cargoCnt(SqlSession session, long itemId){
        return session.selectOne("cargo.cargoCnt",itemId);
    }

    public List<StockDto> selectStock(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectList("cargo.selectStock",stockSearchDto);
    }

    public int getCountStock(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectOne("cargo.countStock",stockSearchDto);
    }

    public List<CargoDto> selectStockStat(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectList("cargo.selectStockStat",stockSearchDto);
    }

    public int getCountStockStat(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectOne("cargo.countStockStat",stockSearchDto);
    }

    public int updateCargoStat(SqlSession session, StockStatDto stockStatDto){
        return session.update("cargo.updateCargoStat", stockStatDto);
    }
}