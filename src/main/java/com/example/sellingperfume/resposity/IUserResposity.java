package com.example.sellingperfume.resposity;

import com.example.sellingperfume.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserResposity extends JpaRepository<UserEntity, Long> {
@Query(value = "SELECT * FROM user where username=:userName",nativeQuery = true)
    public UserEntity getUserByUserName(@Param("userName") String userName);
}
