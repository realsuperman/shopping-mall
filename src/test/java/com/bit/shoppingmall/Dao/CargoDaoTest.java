package com.bit.shoppingmall.Dao;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.domain.Cargo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CargoDaoTest extends RootTest {
    CargoDao cargoDao = new CargoDao();
    @Test
    public void insertTest(){
        List<Cargo> list = new ArrayList<>();
        for(int i=0;i<100;i++) {
            list.add(Cargo.builder().itemId(1L).statusId(3L).build());
        }
        assertEquals(100, cargoDao.insertCargo(sqlSession,list));
    }
}