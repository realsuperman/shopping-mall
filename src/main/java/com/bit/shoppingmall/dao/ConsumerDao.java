package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import javax.swing.text.html.Option;
import java.util.Optional;

public class ConsumerDao {

    public Consumer selectOne(SqlSession session, String k) {
        return session.selectOne("consumer.select", k);
    }

    public int insert(SqlSession session, @Param("consumer") Consumer consumer) {
        return session.insert("consumer.insert", consumer);
    }

}
