package com.example.sellingperfume.services;

import com.example.sellingperfume.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ICategoryServices {
    public CategoryEntity CreateCategory(CategoryEntity categoryEntity);

    public List<CategoryEntity>getAllCategory();

    public void deleteProductType(long id);

    public Optional<CategoryEntity> searchCategoryById(Long id);

    public CategoryEntity updateCategory(CategoryEntity categoryEntity);

}
