package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityResposity extends JpaRepository<AuthorityEntity, Long> {
}
