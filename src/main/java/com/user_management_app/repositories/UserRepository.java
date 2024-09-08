package com.user_management_app.repositories;

import com.user_management_app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    public UserEntity findByEmail(String email);
    public UserEntity findByEmailAndPwd(String email,String pwd);
}
