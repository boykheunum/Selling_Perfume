package com.example.sellingperfume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Controller(value="admin")
public class AdminController {
    @GetMapping(path="adminpage")
    public ModelAndView AdminPage(){
        return new ModelAndView("Admin/AdminPage");
    }
}
