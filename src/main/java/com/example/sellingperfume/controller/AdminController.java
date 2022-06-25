package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.resposity.ICategoryResposity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.ProductServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
public class AdminController {
    @Value("${upload.part}")
    public String upload;
    @Autowired
    CategoryServicesImpl categoryServicesImpl;
    @Autowired
    public CreateTokenInformationUser createTokenInformationUser;

    @Autowired
    public MediaServicesImpl mediaServicesImpl;
    @Autowired
    public ProductServicesImpl productServicesImpl;
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(path = "/adminpage")
    public ModelAndView AdminPage() {
        return new ModelAndView("Admin/AdminPage");
    }

    @GetMapping(path = "addtypeproduct")
    public ModelAndView addTypeProduct() {
        return new ModelAndView("/Admin/AddTypeProduct");
    }

    @PostMapping(path = "createTypeProduct")
    public String createTypeProduct(@RequestBody CategoryEntity categoryEntity, HttpServletRequest rq) throws GeneralSecurityException, IOException {
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

    @GetMapping(path = "addProduct")
    public ModelAndView addProduct(Model model) {
        ModelAndView mv = new ModelAndView("/admin/AddProduct");
        List<CategoryEntity> listCategory = categoryServicesImpl.getAllCategory();
        model.addAttribute("listCategory", listCategory);
        return mv;
    }

    @PostMapping(path = "createProduct")
    public String createProduct(@ModelAttribute ProductEntity productEntity, @RequestParam("images") MultipartFile multipartFile, @RequestParam("productName") String name, HttpSession session) throws GeneralSecurityException, IOException {
        if (session.getAttribute("TokenInfoUser") != null) {
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            String PathUpload = upload + "/ProductAvatar";
            mediaServicesImpl.uploadFile(PathUpload, multipartFile);
            productEntity.setCreateAt(LocalDateTime.now());
            productEntity.setCreateBy((splitToken.split(",")[1]));
            if(multipartFile!=null){
            productEntity.setImage(multipartFile.getOriginalFilename());
            }else {
                productEntity.setImage(null);
            }
            productServicesImpl.createProduct(productEntity);
            logger.info("dat" + name);
            return "homepage";
        }
        return "login";
    }
    @GetMapping(path = "listProducts")
    public ModelAndView listProduct(Model model){
        List<ProductEntity>getAllProduct = productServicesImpl.listProducts();
        model.addAttribute("listProduct", getAllProduct);
        return new ModelAndView("Product/ListProduct");
    }
}
