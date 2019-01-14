package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Repeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatRepository extends JpaRepository<Repeat, Integer> {
    Repeat findById(int id);
}