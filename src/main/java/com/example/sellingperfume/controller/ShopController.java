package com.example.sellingperfume.controller;

import com.example.sellingperfume.Common.TokenCommon;
import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.services.impl.CategoryServicesImpl;
import com.example.sellingperfume.services.impl.MediaServicesImpl;
import com.example.sellingperfume.services.impl.ProductServicesImpl;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
    @Autowired
    private ProductServicesImpl productServicesImpl;
    private static Logger logger = LoggerFactory.getLogger(ShopController.class);

    @GetMapping(path = "homepage")
    private ModelAndView HomePage(Model model) {

        List<ProductEntity> lProductEntity = productServicesImpl.listProducts();
        model.addAttribute("lProductEntity", lProductEntity);
        logger.info(LocalDateTime.now().toString());
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").withZone(DateTimeZone.forID("Asia/Ho_Chi_Minh"));
        logger.info(date.toString(fmt));
        return new ModelAndView("Home/Home");
    }

    @GetMapping(path = "detailProduct")
    private ModelAndView DetailProduct(Model model, @Param("id") long id) {
        ProductEntity productEntity = productServicesImpl.findProductByID(id).get();
        model.addAttribute("productEntity", productEntity);
        return new ModelAndView("Home/DetailProduct");
    }
}
