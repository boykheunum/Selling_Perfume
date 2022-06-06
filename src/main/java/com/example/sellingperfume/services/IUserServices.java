package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.UserEntity;

import java.util.List;

public interface IUserServices {
    public List<UserEntity> getAllUser();

    public UserEntity createUser(UserEntity userEntity);

    public String createSerectKey();

    public byte[] createQRCode(String serectKey, String Username);

    public byte[] confiRQCode(String data, int width, int height);
}
