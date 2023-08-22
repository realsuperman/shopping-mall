package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.dao.OrderSetDao;
import com.bit.shoppingmall.global.GetSessionFactory;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@NoArgsConstructor
public class PaymentService {
    private final OrderDetailDao orderDetailDao = new OrderDetailDao();
    private final OrderSetDao orderSetDao = new OrderSetDao();
    private final SqlSession sqlSession = GetSessionFactory.getInstance().openSession();

    /*
     @Transactional
     1. 주문 페이지에서 결제 버튼을 누른다.
     2. 재고를 체크한다. → Validation?
     3. 재고가 없으면 주문 실패. -> 재고 없는 상품의 id 던져준다.
     4. 재고가 있으면 cargo의 status를 변경한다.
     5. 외부 결제 모듈로 넘어간다.
     6. 결제 실패면 rollback
     7. 결제 성공이면 cart_item에서 delete, order_set, order_detail에 insert
     */

    /*
     @Transactional
     선택한 상품들에 대해 cargo, order_detail의 status를 update한다.
     */
}
