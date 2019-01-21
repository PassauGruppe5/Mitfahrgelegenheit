package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LegRepository extends JpaRepository<Leg, Integer> {
    Leg findById(int id);

    ArrayList<Leg> findByJourney(Journey journey);

    ArrayList<Leg> findLegsByPassengersContaining(User user);


}
