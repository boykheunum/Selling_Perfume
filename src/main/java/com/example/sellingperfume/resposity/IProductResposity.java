package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductResposity extends JpaRepository<ProductEntity,Long> {
    @Query(value = "select * From product WHERE  product_name = :productName", nativeQuery = true)
    public ProductEntity loadProductByName(String productName);
}
