package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductServices {
    public ProductEntity createProduct(ProductEntity productEntity);

    public List<ProductEntity> listProducts();
}
