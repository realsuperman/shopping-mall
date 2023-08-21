package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
import com.bit.shoppingmall.dto.SignUpRequest;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserService {

    private final ConsumerDao consumerDao;
    SqlSession session;
    private static ResourceBundle rb;

    static {
        rb = ResourceBundle.getBundle("application", Locale.KOREA);
    }

    public String alg = rb.getString("encrypt.alg");
    private String key = rb.getString("encrypt.key");
    private String iv = key.substring(0, 16);

    public UserService(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    // 사용자 한 명 조회
    public Consumer readUserOne(String userEamil) {
        return consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), userEamil);
    }

    // 회원가입
    public int signUp(SignUpRequest signUpDto) throws Exception {

        isExistEmail(signUpDto.getUserEmail());
        Validation.validation.validateEmail(signUpDto.getUserEmail());
        Validation.validation.validatePassword(signUpDto.getPassword());

        signUpDto.setPassword(encrypt(signUpDto.getPassword()));
        Consumer consumer = Consumer.signUpDtoToConsumer(signUpDto);

        return consumerDao.insert(GetSessionFactory.getInstance().openSession(), consumer);
    }

    public void isExistEmail(String userEmail) throws Exception {

        if (consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), userEmail) != null) {
            throw new Exception("존재하는 이메일 입니다.");
        }
    }

    // 비밀번호 암호화
    public String encrypt(String originalPassword) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(originalPassword.getBytes("UTF-8"));
        String encryptedPassword = Base64.getEncoder().encodeToString(encrypted);

        return encryptedPassword;
    }

    // 비밀번호 복호화
    public String decrypt(String cipherPassword) throws Exception{
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherPassword);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        return new String(decrypted, "UTF-8");
    }

    // 로그인
    public LoginResponse login(LoginRequest loginRequest) throws Exception {

        Consumer consumer =  consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), loginRequest.getUserEmail());

        if (consumer == null) {
            throw new Exception("존재하지 않는 이메일입니다.");
        }

        if (! loginRequest.getPassword().equals(decrypt(consumer.getPassword()))) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        if (consumer.getIsAdmin() == 0) {

//            int totalPrice = orderService.총구매가격;
//            readUserMemberShip(totalPrice);
        }


        return null;
    }

    // 멤버십 조회
    public void readUserMemberShip(int Price) {

    }



}
