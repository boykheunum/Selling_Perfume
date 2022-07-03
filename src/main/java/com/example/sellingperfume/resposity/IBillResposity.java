package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillResposity extends JpaRepository<BillEntity, Long> {
}
