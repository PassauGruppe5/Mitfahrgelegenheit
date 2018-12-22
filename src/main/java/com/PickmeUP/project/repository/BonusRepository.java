package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
    Bonus findById(int id);
}