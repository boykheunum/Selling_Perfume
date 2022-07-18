package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.DecentralizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDecentralizationResposity extends JpaRepository<DecentralizationEntity, Long> {
}
