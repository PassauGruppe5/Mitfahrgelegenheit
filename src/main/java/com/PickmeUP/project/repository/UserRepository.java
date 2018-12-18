package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findById(int id);

     Boolean existsByEmail(String email);
}