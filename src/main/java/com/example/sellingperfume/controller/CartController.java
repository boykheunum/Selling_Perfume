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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class CartController {
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

    @PostMapping(path = "addToCart")
    public String addToCart(@RequestBody ProductEntity data, HttpSession session, Model model) {
        Map<String, Integer> Cart;
        logger.info(String.valueOf(data.getQuantity()));
        if (session.getAttribute("CartSession") == null) {
            Cart = new HashMap();
            Cart.put(data.getProductName(), data.getQuantity());
        } else {
            Cart = (Map<String, Integer>) session.getAttribute("CartSession");
            if (!Cart.containsKey(data.getProductName())) {
                Cart.put(data.getProductName(), data.getQuantity());
            }else{
                int qty = Cart.get(data.getProductName()) + data.getQuantity();
                Cart.put(data.getProductName(), qty);
            }
        }
        session.setAttribute("CartSession", Cart);
        return "Cart";
    }

    @GetMapping(path="Cart")
    public ModelAndView Cart(Model model, HttpSession session){
        if(session.getAttribute("CartSession")!= null) {
            Map<String,Integer> Cart = (Map<String, Integer>) session.getAttribute("CartSession");
            List<ProductEntity> lProduct = new ArrayList<>();
            Set<String>keys = Cart.keySet();
            for(String key : keys){
                ProductEntity product = productServicesImpl.findProductByName(key);
                product.setQuantity(Cart.get(key));
                lProduct.add(product);
            }
            model.addAttribute("lProduct", lProduct);
        }
        return new ModelAndView("Cart");
    }

    @PostMapping(path="deleteCart")
    public String deleteCart(Model model, HttpSession session, @RequestBody ProductEntity data){
        if(session.getAttribute("CartSession")!= null) {
            Map<String,Integer> Cart = (Map<String, Integer>) session.getAttribute("CartSession");
            if(Cart.containsKey(data.getProductName())) {
                Cart.remove(data.getProductName());
                List<ProductEntity> lProduct = new ArrayList<>();
                Set<String> keys = Cart.keySet();
                for (String key : keys) {
                    ProductEntity product = productServicesImpl.findProductByName(key);
                    product.setQuantity(Cart.get(key));
                    lProduct.add(product);
                }
                model.addAttribute("lProduct", lProduct);
            }
        }
        return "Cart";
    }
}
