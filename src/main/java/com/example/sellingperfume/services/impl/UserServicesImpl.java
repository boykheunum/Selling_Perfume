package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.resposity.IUserResposity;
import com.example.sellingperfume.services.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements IUserServices {
    @Autowired
    public IUserResposity iUserResposity;

    @Override
    public List<UserEntity> getAllUser() {
        return iUserResposity.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return iUserResposity.save(userEntity);
    }
}
