package com.bit.shoppingmall.dao;

import com.bit.shoppingmall.domain.Membership;
import org.apache.ibatis.session.SqlSession;

public class MembershipDao {

    public Membership selectMembershipByPrice(SqlSession session, long price) {

        return session.selectOne("membership.selectMembershipByPrice", price);

    }
}
