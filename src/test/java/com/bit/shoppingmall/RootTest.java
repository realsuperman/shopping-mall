package com.bit.shoppingmall;

import com.bit.shoppingmall.global.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static com.bit.shoppingmall.global.GetSessionFactory.*;

public class RootTest {
    public SqlSession sqlSession;

    @BeforeEach
    void before(){
        sqlSession = getInstance().openSession(false);
        beforeHook();
    }

    @AfterEach
    void after() throws SQLException {
//        sqlSession.commit();
        sqlSession.rollback();
        sqlSession.getConnection().setAutoCommit(true);
        sqlSession.close();
        afterHook();
    }

    @Test
    void connectionTest() throws SQLException {

        for(int i=0;i<1000;i++){
            Connection connection = getInstance().openSession(false).getConnection();
            System.out.println(connection.getAutoCommit());
            connection.close();
        }
    }

    public void beforeHook(){}
    public void afterHook(){}
}
