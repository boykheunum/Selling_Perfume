package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.BillDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillDetailResposity extends JpaRepository<BillDetailEntity, Long> {
}
