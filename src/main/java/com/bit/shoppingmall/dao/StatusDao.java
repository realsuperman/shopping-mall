package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Status;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StatusDao {
    public List<Status> selectAll(SqlSession session){
        return session.selectList("status.selectAll");
    }
}
