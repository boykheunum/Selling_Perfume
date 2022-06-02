package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserResposity extends JpaRepository<UserEntity, Long> {

}
