package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.resposity.ICategoryResposity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.ProductServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {
    @Value("${upload.part}")
    private String upload;
    @Autowired
    private CategoryServicesImpl categoryServicesImpl;
    @Autowired
    private CreateTokenInformationUser createTokenInformationUser;

    @Autowired
    private MediaServicesImpl mediaServicesImpl;
    @Autowired
    private ProductServicesImpl productServicesImpl;
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(path = "/adminpage")
    public ModelAndView AdminPage(Model model) {
        return new ModelAndView("Admin/AdminPage");
    }


}
