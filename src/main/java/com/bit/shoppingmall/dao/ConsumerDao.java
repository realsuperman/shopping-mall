package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.UpdatePasswordRequest;
import com.bit.shoppingmall.dto.UpdateUserRequest;
import org.apache.ibatis.session.SqlSession;

public class ConsumerDao {

    public Consumer selectOne(SqlSession session, String consumerId) {
        return session.selectOne("consumer.select", consumerId);
    }

    public int insert(SqlSession session, Consumer consumer) {
        return session.insert("consumer.insert", consumer);
    }

    public int updatePassword(SqlSession session, UpdatePasswordRequest updatePasswordRequest) {
        return session.update("consumer.updatePassword", updatePasswordRequest);
    }

    public int updateAddress(SqlSession session, UpdateUserRequest updateUserRequest) {
        return session.update("consumer.updateAddress", updateUserRequest);
    }

    public int updatePhoneNumber(SqlSession session, UpdateUserRequest updateUserRequest) {
        return session.update("consumer.updatePhoneNumber", updateUserRequest);
    }

}
