package com.example.sellingperfume.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ShopController {
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
    private static Logger logger = LoggerFactory.getLogger(ShopController.class);

    @GetMapping(path = "homepage")
    public ModelAndView HomePage(Model model) {
        List<ProductEntity> lProductEntity = productServicesImpl.listProducts();
        logger.info(lProductEntity.get(0).getProductName());
        model.addAttribute("lProductEntity", lProductEntity);
        return new ModelAndView("Home/Home");
    }

    @GetMapping(path = "detailProduct")
    public ModelAndView DetailProduct(Model model, @Param("id") long id) {
        ProductEntity productEntity = productServicesImpl.findProductByID(id).get();
        model.addAttribute("productEntity", productEntity);
        return new ModelAndView("Home/DetailProduct");
    }
}
