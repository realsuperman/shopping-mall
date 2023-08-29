package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.dao.MembershipDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Membership;
import com.bit.shoppingmall.dto.*;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.global.GetSessionFactory;
import com.bit.shoppingmall.global.Validation;
import org.apache.ibatis.session.SqlSession;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserService {

    private final ConsumerDao consumerDao;
    private final OrderDetailDao orderDetailDao;
    private final MembershipDao membershipDao;
    private final ResourceBundle rb = ResourceBundle.getBundle("application", Locale.KOREA);
    private final String alg = rb.getString("encrypt.alg");
    private final String key = rb.getString("encrypt.key");
    private final String iv = key.substring(0, 16);
    Validation validation = new Validation();

    public UserService(ConsumerDao consumerDao, OrderDetailDao orderDetailDao, MembershipDao membershipDao) {
        this.consumerDao = consumerDao;
        this.orderDetailDao = orderDetailDao;
        this.membershipDao = membershipDao;
    }

    public Consumer readUserOne(String userEamil) {
        return consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), userEamil);
    }

    /**
     * 로그인
     *
     * @param signUpDto
     * @return int
     */
    public void signUp(SignUpRequest signUpDto) throws InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        try (SqlSession session = GetSessionFactory.getInstance().openSession(true)) {
            isExistEmail(signUpDto.getUserEmail());
            validation.validateEmail(signUpDto.getUserEmail());
            validation.validatePassword(signUpDto.getPassword());

            signUpDto.setPassword(encrypt(signUpDto.getPassword()));
            Consumer consumer = Consumer.signUpDtoToConsumer(signUpDto);
            consumerDao.insert(session, consumer);
        }
    }

    private void isExistEmail(String userEmail) {
        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {
            if (consumerDao.selectOne(session, userEmail) != null) {
                throw new MessageException("존재하는 이메일 입니다.");
            }
        }
    }

    private Cipher cipherSetting() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        return cipher;
    }

    // 비밀번호 암호화
    private String encrypt(String originalPassword) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = cipherSetting();
        byte[] encrypted = cipher.doFinal(originalPassword.getBytes(StandardCharsets.UTF_8));
        String encryptedPassword = Base64.getEncoder().encodeToString(encrypted);
        return encryptedPassword;
    }

    // 비밀번호 복호화
    private String decrypt(String cipherPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = cipherSetting();
        byte[] decodedBytes = Base64.getDecoder().decode(cipherPassword);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 로그인
     *
     * @param loginRequest
     * @return LoginRespons*
     */
    public LoginResponse login(LoginRequest loginRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {

            Consumer consumer = consumerDao.selectOne(session, loginRequest.getUserEmail());

            if (consumer == null) {
                throw new MessageException("존재하지 않는 이메일입니다.");
            }

            if (!loginRequest.getPassword().equals(decrypt(consumer.getPassword()))) {
                throw new MessageException("비밀번호가 일치하지 않습니다.");
            }

            if (consumer.getIsAdmin() == 1) {
                return new LoginResponse(consumer);
            }
            Membership membership = getUserMemberShip(getConsumerTotalBuyPrice(consumer.getConsumerId()));

            return new LoginResponse(consumer, membership.getGrade(), membership.getDiscountRate());
        }
    }

    // 유저의 총 구매 가격 조회
    private long getConsumerTotalBuyPrice(Long consumerId) {

        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {
            Long price = orderDetailDao.getConsumerTotalBuyPrice(session, consumerId);
            if (price == null) {
                return 0;
            }
            return price;
        }
    }

    // 멤버십 조회
    private Membership getUserMemberShip(long totalPrice) {
        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {
            return membershipDao.selectMembershipByPrice(session, totalPrice);
        }
    }

    /**
     * 유저 정보 수정
     *
     * @param
     * @return
     */
    public void updateUserInfo(UpdateUserRequest updateUserRequest) {
        try (SqlSession session = GetSessionFactory.getInstance().openSession(true)) {
            consumerDao.updateUserInfo(session, updateUserRequest);
        }
    }

    /**
     * 비밀번호 수정
     *
     * @param
     * @return
     */

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {

        try (SqlSession session = GetSessionFactory.getInstance().openSession(true)) {

            Consumer consumer = consumerDao.selectOne(session, updatePasswordRequest.getUserEmail());

            if (!updatePasswordRequest.getOriginalPassword().equals(decrypt(consumer.getPassword()))) {
                throw new MessageException("비밀번호가 일치하지 않습니다.");
            }
            validation.validatePassword(updatePasswordRequest.getUpdatePassword());
            updatePasswordRequest.setUpdatePassword(encrypt(updatePasswordRequest.getUpdatePassword()));
            consumerDao.updatePassword(session, updatePasswordRequest);
        }
    }
}