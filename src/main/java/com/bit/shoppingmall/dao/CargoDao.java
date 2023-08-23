package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.dto.StockDto;
import com.bit.shoppingmall.dto.StockSearchDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public class CargoDao {
    public List<Cargo> selectAll(SqlSession session){
        return session.selectList("cargo.selectAll");
    }
    public int insertCargo(SqlSession session, List<Cargo> cargoList) {
        return session.insert("cargo.insertCargo",cargoList);
    }

    public List<StockDto> selectStock(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectList("cargo.selectStock",stockSearchDto);
    }

    public int getCountStock(SqlSession session, StockSearchDto stockSearchDto){
        return session.selectOne("cargo.countStock",stockSearchDto);
    }
}