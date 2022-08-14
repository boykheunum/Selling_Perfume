package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.UserEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public interface IUserServices {
    public List<UserEntity> getAllUser();

    public UserEntity createUser(UserEntity userEntity);

    public String createSerectKey();

    public byte[] createQRCode(String serectKey, String Username);

    public byte[] confiRQCode(String data, int width, int height);

    public UserEntity FindUserByUsername(String usName);

    public boolean checkLogin(UserEntity userEntity, String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeySpecException, InvalidKeyException;

    public boolean checkOtpCode(String otpCode, String serectKey);

    public UserEntity finUserById(Long id);

    public void deleteUser(UserEntity userEntity);

    public StringBuilder ShowButtonAdmin(String str);


}
