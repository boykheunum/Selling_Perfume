package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    public UserServicesImpl userServicesImpl;
    @Autowired
    public CategoryServicesImpl categoryServicesImpl;

    @GetMapping(path="home")
    public ModelAndView Homdepage(){
        return new ModelAndView("home");
    }

    @PostMapping(path = "createRegister", consumes= MediaType.ALL_VALUE)
    public String creatUser(@RequestBody UserEntity userEntity){
        return userServicesImpl.createUser(userEntity).toString();
        //return "thanhcong";
    }

    @GetMapping(path="register")
    public ModelAndView register(){
        return new ModelAndView("Register");
    }

    @GetMapping(path = "listUser")
    public List<UserEntity> listUser(){
        List<UserEntity>listUser = userServicesImpl.getAllUser();
        return listUser;
    }
    @PostMapping(path = "createCategory")
    public CategoryEntity createCategory(@RequestBody CategoryEntity categoryEntity){
        return categoryServicesImpl.CreateCategory(categoryEntity);
    }
}
