package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.TableCache;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AdminService {
    private final CargoDao cargoDao;
    //private final ItemDao itemDao;
    SqlSession session;
    public static TableCache<Cargo> cargoTable = new TableCache<>();



    public AdminService(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
        //this.itemDao = itemDao;
    }

    public List<Cargo> selectAll(){
        if(AdminService.cargoTable.getTable() == null ){ // || 저장시간,요청시간 비교해서 특정 시간 이상이거나 flag가 true라면
            // DB로부터 값을 가져와서 cache를 채우고 flag는 false 시간 잘 기록
        }
        return cargoDao.selectAll(GetSessionFactory.getInstance().openSession());
    }

}