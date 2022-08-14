package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.UserAuthorityEntity;

public interface UserAuthorityServices {
    public UserAuthorityEntity addAuthorityForUser(UserAuthorityEntity userAuthorityEntity);

    public UserAuthorityEntity updateAuthorityForUser(UserAuthorityEntity userAuthorityEntity);

    public UserAuthorityEntity findAuthorityForUser(Long id);

    public StringBuilder checkAuthority(UserAuthorityEntity userAuthorityEntity);
}
