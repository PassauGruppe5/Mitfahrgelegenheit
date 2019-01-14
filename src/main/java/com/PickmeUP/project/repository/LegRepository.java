package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Leg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegRepository extends JpaRepository<Leg, Integer> {
    Leg findById(int id);

}
