package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.AuthorityEntity;

import java.util.List;

public interface AuthorityServices {
    public AuthorityEntity createAuthority(AuthorityEntity authorityEntity);

    public AuthorityEntity UpdateAuthority(Long id, AuthorityEntity authorityEntity);

    public void deleteAuthority(Long id);

    public AuthorityEntity getAuthorityById(Long id, AuthorityEntity Authority);

    public List<AuthorityEntity>getAllAuthority(List<AuthorityEntity>listAuthority);

    public AuthorityEntity getAthorityByName(String nameAuthority);
}
