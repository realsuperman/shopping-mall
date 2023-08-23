package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Cargo;
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
}