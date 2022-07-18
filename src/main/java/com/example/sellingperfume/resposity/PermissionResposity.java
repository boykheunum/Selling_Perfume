package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionResposity extends JpaRepository<PermissionsEntity, Long> {
    @Query(value = "COUNT(*) FROM permissions", nativeQuery = true)
    public int totalPermissions();
}
