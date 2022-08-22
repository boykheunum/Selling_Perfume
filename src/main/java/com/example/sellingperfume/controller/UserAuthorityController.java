package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.UserAuthorityEntity;
import com.example.sellingperfume.services.impl.UserAthorityServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

@RestController
public class UserAuthorityController {
    @Autowired
    private UserAthorityServicesImpl userAthorityServicesImpl;


    private static Logger logger = LoggerFactory.getLogger(UserAuthorityController.class);

    @PostMapping(path = "/addUserAuthority")
    public ResponseEntity<HttpStatus> addUserAuthority(@RequestBody UserAuthorityEntity userAuthorityEntity) throws GeneralSecurityException, IOException {
        try {
            userAuthorityEntity.setCreateAt(LocalDateTime.now());
            userAthorityServicesImpl.addAuthorityForUser(userAuthorityEntity);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ex) {
            logger.info(ex.toString());
            return ResponseEntity.status(HttpStatus.valueOf("ERROR")).build();
        }
    }
}
