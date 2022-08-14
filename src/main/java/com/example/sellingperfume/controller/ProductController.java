package com.example.sellingperfume.controller;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.ProductServicesImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
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
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(path = "addProduct")
    
    private ModelAndView addProduct(Model model) {
        ModelAndView mv = new ModelAndView("/admin/AddProduct");
        List<CategoryEntity> listCategory = categoryServicesImpl.getAllCategory();
        model.addAttribute("listCategory", listCategory);
        return mv;
    }

    @PostMapping(path = "createProduct")
    
    private String createProduct(@ModelAttribute ProductEntity productEntity, @RequestParam("images") MultipartFile multipartFile, @RequestParam("productName") String name, HttpSession session) throws GeneralSecurityException, IOException {
        if (session.getAttribute("TokenInfoUser") != null) {
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            String PathUpload = upload + "/ProductAvatar";
            mediaServicesImpl.uploadFile(PathUpload, multipartFile);
            productEntity.setCreateAt(LocalDateTime.now());
            productEntity.setCreateBy((splitToken.split(",")[1]));
            if (multipartFile != null) {
                productEntity.setImage(multipartFile.getOriginalFilename());
            } else {
                productEntity.setImage(null);
            }
            productServicesImpl.createProduct(productEntity);
            logger.info("dat" + name);
            return "homepage";
        }
        return "login";
    }

    @GetMapping(path = "listProducts")

    private ModelAndView listProduct(Model model) {
        List<ProductEntity> getAllProduct = productServicesImpl.listProducts();
        model.addAttribute("listProduct", getAllProduct);
        return new ModelAndView("Product/ListProduct");
    }

    @GetMapping(path = "updateProduct")
    private ModelAndView updateProduct(Model model, @Param("id") Long id) {
        ModelAndView mv = new ModelAndView("/Product/UpdateProduct");
        Optional<ProductEntity> oProductEntity = productServicesImpl.findProductByID(id);
        ProductEntity productEntity = oProductEntity.get();
        model.addAttribute("productEntity", productEntity);
        List<CategoryEntity> listCategory = categoryServicesImpl.getAllCategory();
        model.addAttribute("listCategory", listCategory);

        return mv;
    }

    @PostMapping(path = "UpdateProduct")
   
    private String UpdateProduct(@ModelAttribute ProductEntity productEntityById, @RequestParam("images") MultipartFile multipartFile, @RequestParam("productName") String name, HttpSession session, @RequestParam("id") long id) throws GeneralSecurityException, IOException {
        if (session.getAttribute("TokenInfoUser") != null) {
            logger.info(String.valueOf(id));
            Optional<ProductEntity> oProductEntity = productServicesImpl.findProductByID(id);
            ProductEntity productEntity = oProductEntity.get();
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            String splitToken = createTokenInformationUser.decryptTokenUser(tokenInforUser);
            String PathUpload = upload + "/ProductAvatar";
            mediaServicesImpl.uploadFile(PathUpload, multipartFile);
            productEntity.setUpdateAt(LocalDateTime.now());
            productEntity.setUpdateBy((splitToken.split(",")[1]));
            productEntity.setProductName(productEntityById.getProductName());
            productEntity.setCatagory_id(productEntityById.getCatagory_id());
            productEntity.setDescribe(productEntityById.getDescribe());
            productEntity.setInputPrice(productEntityById.getInputPrice());
            productEntity.setPrice(productEntityById.getPrice());
            if (multipartFile != null) {
                productEntity.setImage(multipartFile.getOriginalFilename());
            } else {
                productEntity.setImage(null);
            }
            logger.info(productEntityById.getProductName());
            productServicesImpl.createProduct(productEntity);
            logger.info("dat" + name);
            return "homepage";
        }
        return "login";
    }

    @GetMapping(path = "deleteProduct")
    private ModelAndView deleteProduct(@Param("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("TokenInfoUser") != null) {
            productServicesImpl.deleteProduct(id);
            String tokenInforUser = session.getAttribute("TokenInfoUser").toString();
            return listProduct(model);
        }
        return new ModelAndView("login");
    }
}
