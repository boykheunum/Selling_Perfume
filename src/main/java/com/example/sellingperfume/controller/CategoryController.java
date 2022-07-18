package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Value("${upload.part}")
    private String upload;
    @Autowired
    CategoryServicesImpl categoryServicesImpl;
    @Autowired
    private CreateTokenInformationUser createTokenInformationUser;
    @Autowired
    private MediaServicesImpl mediaServicesImpl;
    @Autowired
    private ProductServicesImpl productServicesImpl;

    @GetMapping(path = "addCategory")
    private ModelAndView addTypeProduct() {
        return new ModelAndView("/Admin/AddTypeProduct");
    }

    @PostMapping(path = "createCategory")
    private String createTypeProduct(@RequestBody CategoryEntity categoryEntity, HttpServletRequest rq) throws GeneralSecurityException, IOException {
        HttpSession session = rq.getSession();
        if (session.getAttribute("TokenInfoUser") != null) {
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            logger.info("tokken: " + tokenInforUser);
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            logger.info(splitToken);
            categoryEntity.setCreateAt(LocalDateTime.now());
            categoryEntity.setCreateBy(splitToken.split(",")[1]);
            categoryServicesImpl.CreateCategory(categoryEntity);
            return "adminpage";
        }
        return "login";
    }

    @GetMapping(path = "listCategory")
    private ModelAndView listProductType(Model model) {
        List<CategoryEntity> lCategoryEntity = categoryServicesImpl.getAllCategory();
        model.addAttribute("categoryEntity", lCategoryEntity);
        return new ModelAndView("/Category/ListCategory");
    }

    @GetMapping(path = "updateCategory")
    private ModelAndView updateproducttype(HttpSession session, @Param("id") long id, Model model) throws GeneralSecurityException, IOException {
        logger.info(String.valueOf(id));
        if (session.getAttribute("TokenInfoUser") != null) {
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            Optional<CategoryEntity> oCategoryEntity = categoryServicesImpl.searchCategoryById(id);
            CategoryEntity categoryEntity = oCategoryEntity.get();
            model.addAttribute("categoryEntity", categoryEntity);
            return new ModelAndView("Category/UpdateCategory");
        }
        return new ModelAndView("login");
    }
}
