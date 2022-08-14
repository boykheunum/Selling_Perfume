package com.example.sellingperfume.services.impl;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@Service
public class CreateTokenInformationUser {
    public static final String TITLE = "1234qwer";
    public static final String DELIMITERSCHARACTER = ",";

    private static String base64Encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static SecretKeySpec getKeyEncrypt(String user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = user == null ? "" : user;
        byte[] salt = new String("20180201").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = createSecretKey(password.toCharArray(), salt,
                iterationCount, keyLength);
        return key;
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encryptAD(String originalPassword, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(originalPassword.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    public static String createTokenValue(String username, String userAuthority, String isLogin) throws Exception {
        String value = TITLE + DELIMITERSCHARACTER + username + DELIMITERSCHARACTER + userAuthority + DELIMITERSCHARACTER + isLogin;
        value = encryptAD(value, getKeyEncrypt("tokenKey"));
        return value;//return base64
    }

    public static String decrypt(String encryptedPassword, SecretKeySpec key) {
        String iv = encryptedPassword.split(":")[0];
        String property = encryptedPassword.split(":")[1];
        Cipher pbeCipher = null;
        try {
            pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decryptTokenUser(String userToken) throws GeneralSecurityException, IOException {
        if (userToken == null) {
            return null;
        }
        SecretKeySpec key = getKeyEncrypt("tokenKey");
        return decrypt(userToken, key);
    }

    private static byte[] base64Decode(String property) throws IOException {
        return DatatypeConverter.parseBase64Binary(property);
    }
}
