package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.CategoryEntity;

import java.util.List;

public interface ICategoryServices {
    public CategoryEntity CreateCategory(CategoryEntity categoryEntity);

    public List<CategoryEntity>getAllCategory();
}
