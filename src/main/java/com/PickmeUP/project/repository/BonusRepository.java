package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {

    Bonus findById(int bonus_id);
}
