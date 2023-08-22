package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.StatusDao;
import com.bit.shoppingmall.domain.Status;
import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StatusService {
    private final StatusDao statusDao;

    public StatusService(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    public List<Status> selectAll(){
        try (SqlSession sqlSession = GetSessionFactory.getInstance().openSession()) {
            return statusDao.selectAll(sqlSession);
        }
    }

}