package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.ItemDao;
import com.bit.shoppingmall.domain.Cargo;
import com.bit.shoppingmall.domain.Item;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final ItemDao itemDao;
    private final CargoDao cargoDao;

    public ItemService(ItemDao itemDao, CargoDao cargoDao) {
        this.itemDao = itemDao;
        this.cargoDao=cargoDao;
    }

    public void insertItem(Item item, int count)  {
        SqlSession sqlSession = GetSessionFactory.getInstance().openSession(false);
        try{
            itemDao.insertItem(sqlSession,item);
            List<Cargo> cargoList = new ArrayList<>();
            for(int i=0;i<count;i++){
                cargoList.add(Cargo.builder().itemId(item.getItemId()).statusId(3L).build());
            }
            cargoDao.insertCargo(sqlSession,cargoList);

            sqlSession.commit();
        }catch(Exception e){
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }

}
