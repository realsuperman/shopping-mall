package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.ConsumerDao;
import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.dto.LoginRequest;
import com.bit.shoppingmall.dto.LoginResponse;
import com.bit.shoppingmall.dto.SignUpRequest;
import com.bit.shoppingmall.global.GetSessionFactory;
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

        if (isExistEmail(signUpDto.getUserEmail())) {
            throw new Exception("존재하는 이메일 입니다.");
        }

        signUpDto.setPassword(encrypt(signUpDto.getPassword()));
        Consumer consumer = Consumer.signUpDtoToConsumer(signUpDto);

        return consumerDao.insert(GetSessionFactory.getInstance().openSession(), consumer);
    }

    public boolean isExistEmail(String userEmail) {

        if (consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), userEmail) {
            return true;
        }
        return false;
    }

    public void dataValidation(SignUpRequest signUpDto) throws Exception {

        int at = signUpDto.getUserEmail().indexOf("@");
        int dot = signUpDto.getUserEmail().indexOf(".");

        if (at == -1 || dot == -1 || at > dot) {
            throw new Exception("이메일 양식에 맞지 않습니다. 다시 시도해주세요.");
        }


    }


    // 비밀번호 암호화
    public String encrypt(String originalPassword) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

//        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(originalPassword.getBytes("UTF-8"));
        String encryptedPassword = Base64.getEncoder().encodeToString(encrypted);

        return encryptedPassword;
    }

    // 로그인
    public LoginResponse login(LoginRequest loginRequest){
        consumerDao.selectOne(GetSessionFactory.getInstance().openSession(), loginRequest.getUserEmail());
        return null;
    }

}
