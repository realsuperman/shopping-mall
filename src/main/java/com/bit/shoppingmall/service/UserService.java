package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.dao.MembershipDao;
import com.bit.shoppingmall.dao.OrderDetailDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.domain.Membership;
import com.bit.shoppingmall.dto.*;
import com.bit.shoppingmall.exception.DuplicateKeyException;
import com.bit.shoppingmall.exception.NoSuchDataException;
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

    Validation validation = new Validation();

    private final ResourceBundle rb = ResourceBundle.getBundle("application", Locale.KOREA);

    private final String alg = rb.getString("encrypt.alg");
    private final String key = rb.getString("encrypt.key");
    private final String iv = key.substring(0, 16);

    public UserService(ConsumerDao consumerDao, OrderDetailDao orderDetailDao, MembershipDao membershipDao) {
        this.consumerDao = consumerDao;
        this.orderDetailDao = orderDetailDao;
        this.membershipDao = membershipDao;
    }

    // 사용자 한 명 조회 -- 단순 테스트용
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

    public void isExistEmail(String userEmail) {

        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {

            if (consumerDao.selectOne(session, userEmail) != null) {
                throw new DuplicateKeyException("존재하는 이메일 입니다.");
            }
        }
    }

    // 비밀번호 암호화
    public String encrypt(String originalPassword) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(originalPassword.getBytes(StandardCharsets.UTF_8));
        String encryptedPassword = Base64.getEncoder().encodeToString(encrypted);

        return encryptedPassword;
    }

    // 비밀번호 복호화
    public String decrypt(String cipherPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

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
                throw new NoSuchDataException("존재하지 않는 이메일입니다.");
            }

            if (!loginRequest.getPassword().equals(decrypt(consumer.getPassword()))) {
                throw new NoSuchDataException("비밀번호가 일치하지 않습니다.");
            }

            if (consumer.getIsAdmin() == 1) {
                return new LoginResponse(consumer);
            }

            long totalPrice = getConsumerTotalBuyPrice(consumer.getConsumerId());
            Membership membership = getUserMemberShip(totalPrice);

            return new LoginResponse(consumer, membership.getGrade(), membership.getDiscountRate());
        }
    }

    // 유저의 총 구매 가격 조회
    public long getConsumerTotalBuyPrice(Long consumerId) {

        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {
            Long price = orderDetailDao.getConsumerTotalBuyPrice(session, consumerId);
            if (price == null) {
                return 0;
            }
            return price;
        }
    }

    // 멤버십 조회
    public Membership getUserMemberShip(long totalPrice) {
        try (SqlSession session = GetSessionFactory.getInstance().openSession()) {
            return membershipDao.selectMembershipByPrice(session, totalPrice);
        }
    }

    /**
     * 유저 정보 수정
     *
     * @param
     * @return - consumer 그대로 사용하지 말고, dto로 받아, service 단에서 변환해서 쓸까나
     * - 주소 변경, 휴대폰 번호 변경 분리? controller 에서 구분해서 서비스에서 따로 던지기
     */
    public void updatePhoneNumber(UpdateUserRequest updateUserRequest) {
        try (SqlSession session = GetSessionFactory.getInstance().openSession(true)) {
            consumerDao.updatePhoneNumber(session, updateUserRequest);
        }
    }

    /**
     * 유저 정보 수정
     *
     * @param
     * @return - consumer 그대로 사용하지 말고, dto로 받아, service 단에서 변환해서 쓸까나
     * - 주소 변경, 휴대폰 번호 변경 분리? controller 에서 구분해서 서비스에서 따로 던지기
     */
    public void updateAddress(UpdateUserRequest updateUserRequest) {

        try (SqlSession session = GetSessionFactory.getInstance().openSession(true)) {
            consumerDao.updateAddress(session, updateUserRequest);
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
                throw new NoSuchDataException("비밀번호가 일치하지 않습니다.");
            }
            validation.validatePassword(updatePasswordRequest.getUpdatePassword());
            updatePasswordRequest.setUpdatePassword(encrypt(updatePasswordRequest.getUpdatePassword()));
            consumerDao.updatePassword(session, updatePasswordRequest);
        }
    }
}
