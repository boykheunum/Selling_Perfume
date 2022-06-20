package com.example.sellingperfume.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ShopController {
    @GetMapping(path="homepage")
    public ModelAndView HomePage(){
        return new ModelAndView("Home/Home");
    }
}
