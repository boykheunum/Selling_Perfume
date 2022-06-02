package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public interface ICategoryResposity extends JpaRepository<CategoryEntity, Long> {

}
