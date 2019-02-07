package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {

    //      finds Bonus by id.
    //
    //      @param bonus_id             id to be looked for.
    //      @return Bonus               the Bonus.
    Bonus findById(int bonus_id);
}
