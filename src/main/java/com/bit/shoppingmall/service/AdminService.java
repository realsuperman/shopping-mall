package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AdminService {
    private final CargoDao cargoDao;
    //private final ItemDao itemDao;
    SqlSession session;



    public AdminService(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
        //this.itemDao = itemDao;
    }

    public List<Cargo> selectAll(){
        return cargoDao.selectAll(GetSessionFactory.getInstance().openSession());
    }

}