package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.dto.SignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class UserServiceTest extends RootTest {
    private UserService userService;

    // 단순 조회 확인용
    @Test
    void selectOne() {
        System.out.println( userService.readUserOne("cso6005@naver.com"));
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {

        SignUpRequest signUpDto = new SignUpRequest("b1@naver.com", "12345", "최소", "01034229999", "부산광역시");

        Assertions.assertEquals(1, userService.signUp(signUpDto));

//        Consumer consumer = userService.readUserOne("b1@naver.com");
//        System.out.println(consumer);
//        Assertions.assertEquals(signUpDto.getUserEmail(), consumer.getUserEmail() );

    }

    @Override
    public void beforeHook() {
        super.beforeHook();
        userService = new UserService(new ConsumerDao());
    }
}
