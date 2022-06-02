package com.example.sellingperfume.services.impl;

import com.example.sellingperfume.entity.CategoryEntity;
import com.example.sellingperfume.resposity.ICategoryResposity;
import com.example.sellingperfume.services.ICategoryServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServicesImpl implements ICategoryServices {
    @Autowired
    ICategoryResposity iCategoryResposity;
    @Override
    public CategoryEntity CreateCategory(CategoryEntity categoryEntity) {
        categoryEntity.setCreateAt(LocalDateTime.now());
        return iCategoryResposity.save(categoryEntity);
    }
}
