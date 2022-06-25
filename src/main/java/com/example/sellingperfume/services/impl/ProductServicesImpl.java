package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.ProductEntity;
import com.example.sellingperfume.resposity.IProductResposity;
import com.example.sellingperfume.resposity.IUserResposity;
import com.example.sellingperfume.services.IProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServicesImpl implements IProductServices {
    @Autowired
    IProductResposity iProductResposity;
    @Override
    public ProductEntity createProduct(ProductEntity productEntity) {
        return iProductResposity.save(productEntity);
    }

    @Override
    public List<ProductEntity> listProducts() {
        List<ProductEntity>listGetAllProduct = iProductResposity.findAll();
        return listGetAllProduct;
    }
}
