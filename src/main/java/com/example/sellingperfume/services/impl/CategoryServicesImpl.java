package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.resposity.ICategoryResposity;
import com.example.sellingperfume.services.ICategoryServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServicesImpl implements ICategoryServices {
    @Autowired
    ICategoryResposity iCategoryResposity;
    @Override
    public CategoryEntity CreateCategory(CategoryEntity categoryEntity) {
//        categoryEntity.setCreateAt(LocalDateTime.now());
        return iCategoryResposity.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        List<CategoryEntity> listCategory = iCategoryResposity.findAll();
        return listCategory;
    }

    @Override
    public void deleteProductType(long id) {
        iCategoryResposity.deleteById(id);
    }

    @Override
    public Optional<CategoryEntity> searchCategoryById(Long id) {
        return iCategoryResposity.findById(id);
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        return iCategoryResposity.save(categoryEntity);
    }


}
