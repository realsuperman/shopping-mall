package com.bit.shoppingmall.service;

import com.bit.shoppingmall.RootTest;
import com.bit.shoppingmall.controller.UserController;
import com.bit.shoppingmall.controller.UserInfoController;
import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.dao.MembershipDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.SignUpRequest;
import com.bit.shoppingmall.dto.UpdatePasswordRequest;
import com.bit.shoppingmall.dto.UpdateUserRequest;

import com.bit.shoppingmall.exception.MessageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest extends RootTest {
    private UserService userService;

    @Test
    @DisplayName("단순 조회 테스트")
    void selectOne() {
        System.out.println( userService.readUserOne("cso6005@naver.com"));
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {

        SignUpRequest signUpDto = new SignUpRequest("b1111@naver.com", "12345", "최소", "01034229999", "부산광역시");
        userService.signUp(signUpDto);
        //Assertions.assertEquals(1, userService.signUp(signUpDto));

        Consumer consumer = userService.readUserOne("b1111@naver.com");
        System.out.println(consumer);
//        Assertions.assertEquals(signUpDto.getUserEmail(), consumer.getUserEmail() );

    }

    @Test
    @DisplayName("회원가입 data validation 테스트")
    void signUpVaildation() throws Exception {

        SignUpRequest signUpDto1 = new SignUpRequest("b1naver.com", "12345", "최소", "01034229999", "부산광역시");
        SignUpRequest signUpDto2 = new SignUpRequest("b1@naver.com", "12", "최소", "01034229999", "부산광역시");
        SignUpRequest signUpDto3 = new SignUpRequest("cso6005@naver.com", "12", "최소", "01034229999", "부산광역시");
        Exception exception1 = Assertions.assertThrows(MessageException.class, () -> userService.signUp(signUpDto1));
        Exception exception2 = Assertions.assertThrows(MessageException.class, () -> userService.signUp(signUpDto2));
        Exception exception3 = Assertions.assertThrows(MessageException.class, () -> userService.signUp(signUpDto3));

        Assertions.assertEquals("이메일 양식에 맞지 않습니다.", exception1.getMessage());
        Assertions.assertEquals("비밀번호는 5글자 이상 가능합니다.", exception2.getMessage());
        Assertions.assertEquals("존재하는 이메일 입니다.", exception3.getMessage());

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {

        // 주문을 한 번이라도 한 고객
        LoginRequest loginRequest = new LoginRequest("a1234@naver.com", "a123456");

        // 주문을 한 번이라도 안한 고객
        LoginRequest loginRequest2 = new LoginRequest("cso6005@naver.com", "b123456");

//        LoginResponse loginResponse = userService.login(loginRequest);
//        LoginResponse loginResponse2 = userService.login(loginRequest2);

//        System.out.println(loginResponse);
//        System.out.println(loginResponse2);

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFail() throws Exception {

        LoginRequest loginRequest1 = new LoginRequest("cso6@naver.com", "12345"); // 이메일 틀린
        LoginRequest loginRequest2 = new LoginRequest("cso6005@naver.com", "12"); // 비번 틀린
        Exception exception1 = Assertions.assertThrows(MessageException.class, () -> userService.login(loginRequest1));
        Exception exception2 = Assertions.assertThrows(MessageException.class, () -> userService.login(loginRequest2));
        Assertions.assertEquals("존재하지 않는 이메일입니다.", exception1.getMessage());
        Assertions.assertEquals("비밀번호가 일치하지 않습니다.", exception2.getMessage());

    }

    @Test
    @DisplayName("패스워드 변경 테스트")
    void updatePassword() throws Exception {
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("cso6005@naver.com", "c111111", "111111");

        userService.updatePassword(updatePasswordRequest);
        //        Assertions.assertEquals(1, userService.updatePassword(updatePasswordRequest));
        System.out.println( userService.readUserOne("cso6005@naver.com") );

    }

    @Test
    @DisplayName("휴대폰 번호 변경 테스트")
    void updatePhoneNumber() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("cso6005@naver.com",  null,"01033312222");
//        Assertions.assertEquals(1, userService.updatePhoneNumber(updateUserRequest));

        userService.updateUserInfo(updateUserRequest);

        System.out.println( userService.readUserOne("cso6005@naver.com"));

    }

    @Test
    @DisplayName("주소 변경 테스트")
    void updateAddress() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("cso6005@naver.com", "대전광역시", null);
//        Assertions.assertEquals(1, userService.updateAddress(updateUserRequest));
        userService.updateUserInfo(updateUserRequest);
    }

    @Test
    @DisplayName("주소, 휴대폰 변경 테스트")
    void updateInfo() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("cso6005@naver.com", "부산광역시", "01098989898");
//        Assertions.assertEquals(1, userService.updateAddress(updateUserRequest));
        userService.updateUserInfo(updateUserRequest);
    }

    @Override
    public void beforeHook() {
        super.beforeHook();
        userService = new UserService(
                new ConsumerDao(), new OrderDetailDao(), new MembershipDao()
        );
    }
}
