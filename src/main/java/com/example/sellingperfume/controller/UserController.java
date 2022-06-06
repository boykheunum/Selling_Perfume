package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class UserController {
    @Value("${upload.part}")
    public String upload;
    @Autowired
    public UserServicesImpl userServicesImpl;
    @Autowired
    public CategoryServicesImpl categoryServicesImpl;
    public MediaServicesImpl mediaServices;

    @GetMapping(path = "home")
    public ModelAndView Homdepage() {
        return new ModelAndView("home");
    }

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping(path = "createRegister")
    public String creatUser(@ModelAttribute UserEntity userEntity, HttpSession session, @RequestParam("avatars") MultipartFile multipartFile) {
        logger.info("userEntity = "+userEntity.getAvatar());
        logger.info("TD ="+multipartFile);
        String PathUpload = upload + "/UserAvatar";

        session.setAttribute("userName", userEntity.getUsername());
        userEntity.setCreateAt(LocalDateTime.now());
        userEntity.setCreateBy(userEntity.getId());
        if (userEntity.getActive_otp() == true) {
            userEntity.setSerectKey(userServicesImpl.createSerectKey());
            userServicesImpl.createQRCode(userEntity.getUsername(), userEntity.getSerectKey()).toString();

        }
        return userServicesImpl.createUser(userEntity).toString();
    }

    @GetMapping(path = "register")
    public ModelAndView register() {
        return new ModelAndView("Register");
    }

    @GetMapping(path = "listUser")
    public List<UserEntity> listUser() {
        List<UserEntity> listUser = userServicesImpl.getAllUser();
        return listUser;
    }

    @PostMapping(path = "createCategory")
    public CategoryEntity createCategory(@RequestBody CategoryEntity categoryEntity) {
        return categoryServicesImpl.CreateCategory(categoryEntity);
    }


    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("avatars") MultipartFile file,@ModelAttribute UserEntity userEntity) {

        String fileName = file.getOriginalFilename();
        logger.info("userEntity = "+userEntity.getActive_otp());
        logger.info("fileName = "+userEntity.getBirthday());
        return ResponseEntity.ok("File uploaded successfully.");
    }

}
