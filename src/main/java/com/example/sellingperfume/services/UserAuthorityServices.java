package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.entity.UserAuthorityEntity;
import com.example.sellingperfume.entity.UserEntity;

public interface UserAuthorityServices {
    public UserAuthorityEntity addAuthorityForUser(UserAuthorityEntity userAuthorityEntity);

    public UserAuthorityEntity updateAuthorityForUser(UserAuthorityEntity userAuthorityEntity);

    public UserAuthorityEntity findAuthorityForUser(Long id);

    public StringBuilder setAuthorityIntoken(UserAuthorityEntity userAuthorityEntity);

    public boolean checkUserAuthority(UserEntity userEntity, AuthorityEntity authorityEntity);
}
