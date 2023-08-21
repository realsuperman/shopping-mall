package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.dao.MembershipDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
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

//    @Test
    @DisplayName("단순 조회 테스트")
    void selectOne() {
        System.out.println( userService.readUserOne("cso6005@naver.com"));
    }

//    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {

        SignUpRequest signUpDto = new SignUpRequest("b1@naver.com", "12345", "최소", "01034229999", "부산광역시");

        Assertions.assertEquals(1, userService.signUp(signUpDto));

//        Consumer consumer = userService.readUserOne("b1@naver.com");
//        System.out.println(consumer);
//        Assertions.assertEquals(signUpDto.getUserEmail(), consumer.getUserEmail() );

    }

//    @Test
    @DisplayName("회원가입 data validation 테스트")
    void signUpVaildation() throws Exception {

        SignUpRequest signUpDto1 = new SignUpRequest("b1naver.com", "12345", "최소", "01034229999", "부산광역시");
        SignUpRequest signUpDto2 = new SignUpRequest("b1@naver.com", "12", "최소", "01034229999", "부산광역시");
        SignUpRequest signUpDto3 = new SignUpRequest("cso6005@naver.com", "12", "최소", "01034229999", "부산광역시");
        Exception exception1 = Assertions.assertThrows(Exception.class, () -> userService.signUp(signUpDto1));
        Exception exception2 = Assertions.assertThrows(Exception.class, () -> userService.signUp(signUpDto2));
        Exception exception3 = Assertions.assertThrows(Exception.class, () -> userService.signUp(signUpDto3));

        Assertions.assertEquals("이메일 양식에 맞지 않습니다.", exception1.getMessage());
        Assertions.assertEquals("비밀번호는 5글자 이상 가능합니다.", exception2.getMessage());
        Assertions.assertEquals("존재하는 이메일 입니다.", exception3.getMessage());

    }

    @Test
    @DisplayName("로그인 성공 테스트 - 주문을 한 번이라도 한 고객")
    void loginSuccess() throws Exception {

        // 주문을 한 번이라도 한 고객
        LoginRequest loginRequest = new LoginRequest("a1234@naver.com", "a123456");

        // 주문을 한 번이라도 안한 고객
        LoginRequest loginRequest2 = new LoginRequest("cso6005@naver.com", "b123456");

//        LoginResponse loginResponse = userService.login(loginRequest);
        userService.login(loginRequest2);
//        LoginResponse loginResponse2 = userService.login(loginRequest2);

//        System.out.println(loginResponse);
//        System.out.println(loginResponse2);

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFail() throws Exception {

        LoginRequest loginRequest1 = new LoginRequest("cso6@naver.com", "12345"); // 이메일 틀린
        LoginRequest loginRequest2 = new LoginRequest("cso6005@naver.com", "12"); // 비번 틀린
        Exception exception1 = Assertions.assertThrows(Exception.class, () -> userService.login(loginRequest1));
        Exception exception2 = Assertions.assertThrows(Exception.class, () -> userService.login(loginRequest2));
        Assertions.assertEquals("존재하지 않는 이메일입니다.", exception1.getMessage());
        Assertions.assertEquals("비밀번호가 일치하지 않습니다.", exception2.getMessage());

    }

    @Override
    public void beforeHook() {
        super.beforeHook();
        userService = new UserService(
                new ConsumerDao(), new OrderDetailDao(), new MembershipDao()
        );
    }
}
