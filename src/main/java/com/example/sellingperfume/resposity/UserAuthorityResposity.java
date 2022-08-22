package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityResposity extends JpaRepository<UserAuthorityEntity, Long> {
    @Query(value = "SELECT * FROM user_authority WHERE user_id = :userId AND authority_id = :authorityId", nativeQuery = true)
    public UserAuthorityEntity checkUserAuth(Long userId, Long authorityId);
}
