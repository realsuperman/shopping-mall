package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Cargo;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public class CargoDao {
    public List<Cargo> selectAll(SqlSession session){
        return session.selectList("cargo.selectAll");
    }
}

/*
    admin -> 트랜잭션?
    cargo, item

 */