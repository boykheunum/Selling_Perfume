package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.services.impl.AuthorityServicesImpl;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    @Value("${upload.part}")
    private String upload;
    @Autowired
    private CategoryServicesImpl categoryServicesImpl;
    @Autowired
    private CreateTokenInformationUser createTokenInformationUser;
    @Autowired
    private AuthorityServicesImpl authorityServicesImpl;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(path = "/adminpage")
    public ModelAndView AdminPage(Model model) {
        return new ModelAndView("Admin/AdminPage");
    }

}
