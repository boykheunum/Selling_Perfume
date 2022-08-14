package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.UserAuthorityEntity;
import com.example.sellingperfume.resposity.UserAuthorityResposity;
import com.example.sellingperfume.services.UserAuthorityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAthorityServicesImpl implements UserAuthorityServices {
    @Autowired
    private UserAuthorityResposity userAuthorityResposity;
    @Override
    public UserAuthorityEntity addAuthorityForUser(UserAuthorityEntity userAuthorityEntity) {
        return userAuthorityResposity.save(userAuthorityEntity);
    }

    @Override
    public UserAuthorityEntity updateAuthorityForUser(UserAuthorityEntity userAuthorityEntity) {
        return userAuthorityResposity.save(userAuthorityEntity);
    }

    @Override
    public UserAuthorityEntity findAuthorityForUser(Long id) {
        return userAuthorityResposity.findById(id).get();
    }

    @Override
    public StringBuilder checkAuthority(UserAuthorityEntity userAuthorityEntity) {
        StringBuilder strBuilder = new StringBuilder("00000");
        if(!userAuthorityEntity.getViewAuthority().isEmpty() && userAuthorityEntity.getViewAuthority().equals("1")){
            strBuilder.setCharAt(0,'1');
        }
        if(!userAuthorityEntity.getCreateAuthority().isEmpty() && userAuthorityEntity.getCreateAuthority().equals("1")){
            strBuilder.setCharAt(1,'1');
        }
        if(!userAuthorityEntity.getUpdateAuthority().isEmpty() && userAuthorityEntity.getUpdateAuthority().equals("1")){
            strBuilder.setCharAt(2,'1');
        }
        if(!userAuthorityEntity.getDeleteAuthority().isEmpty() && userAuthorityEntity.getDeleteAuthority().equals("1")){
            strBuilder.setCharAt(3,'1');
        }
        if(!userAuthorityEntity.getExportAuthority().isEmpty() && userAuthorityEntity.getExportAuthority().equals("1")){
            strBuilder.setCharAt(4,'1');
        }
        return strBuilder;
    }
}
