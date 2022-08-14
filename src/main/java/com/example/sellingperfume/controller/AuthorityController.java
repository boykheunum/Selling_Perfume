package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.services.impl.AuthorityServicesImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

public class AuthorityController {
    @Autowired
    private AuthorityServicesImpl authorityServicesImpl;

    private static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @PostMapping(path = "/addAuthority")
    public ResponseEntity<AuthorityEntity> createAuthority(@RequestBody AuthorityEntity authorityEntity){
        AuthorityEntity authority = new AuthorityEntity();
        authority.setCreateAt(LocalDateTime.now());
        authority = authorityServicesImpl.createAuthority(authorityEntity);
        if(authority==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(authority);
    }

    @DeleteMapping(path = "/deleteAthority")
    public ResponseEntity<HttpStatus> deleteAuthority(@RequestParam(value = "id") Long id){
        try{
            authorityServicesImpl.deleteAuthority(id);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/updateAuthority")
    public ResponseEntity<HttpStatus> updateAuthority(@RequestParam(value = "id") Long id, @RequestBody AuthorityEntity authorityEntity){
        try{
            authorityServicesImpl.UpdateAuthority(id, authorityEntity);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
