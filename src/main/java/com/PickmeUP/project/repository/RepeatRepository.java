package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Repeats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatRepository extends JpaRepository<Repeats, Long> {
    Repeats findById(int id);
}