package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.resposity.AuthorityResposity;
import com.example.sellingperfume.services.AuthorityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServicesImpl implements AuthorityServices {
    @Autowired
    private AuthorityResposity authorityResposity;

    @Override
    public AuthorityEntity createAuthority(AuthorityEntity authorityEntity) {
        return authorityResposity.save(authorityEntity);
    }

    @Override
    public AuthorityEntity UpdateAuthority(Long id, AuthorityEntity authorityEntity) {
        authorityEntity = authorityResposity.findById(id).get();
        return authorityResposity.save(authorityEntity);
    }

    @Override
    public void deleteAuthority(Long id) {
        AuthorityEntity authorityEntity = authorityResposity.findById(id).get();
        authorityResposity.delete(authorityEntity);
    }

    @Override
    public AuthorityEntity getAuthorityById(Long id, AuthorityEntity Authority) {
        Authority = authorityResposity.findById(id).get();
        return Authority;
    }

    @Override
    public List<AuthorityEntity> getAllAuthority(List<AuthorityEntity> listAuthority) {
        listAuthority = authorityResposity.findAll();
        return listAuthority;
    }
}
