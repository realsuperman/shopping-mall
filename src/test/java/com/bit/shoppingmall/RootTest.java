package com.bit.shoppingmall;

import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class RootTest {
    SqlSession sqlSession;

    @BeforeEach
    void before(){
        sqlSession = GetSessionFactory.getInstance().openSession(false);
        beforeHook();
    }

    @AfterEach
    void after() throws SQLException {
//        sqlSession.commit();
        sqlSession.rollback();
        sqlSession.getConnection().setAutoCommit(true);
        afterHook();
    }

    public void beforeHook(){}
    public void afterHook(){}
}
