package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Stop findById(int id);
}