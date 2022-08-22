package com.example.sellingperfume.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @GetMapping(path = "/adminpage")
    public ModelAndView AdminPage(Model model) {
        return new ModelAndView("Admin/AdminPage");
    }

}
