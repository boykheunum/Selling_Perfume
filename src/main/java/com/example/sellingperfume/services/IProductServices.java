package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IProductServices {
    public ProductEntity createProduct(ProductEntity productEntity);

    public List<ProductEntity> listProducts();

    public ProductEntity UpdateProduct(ProductEntity productEntity);

    public Optional<ProductEntity> findProductByID(long id);

    public void deleteProduct(long id);

    public ProductEntity findProductByName(String ProductName);
}
