package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LegRepository extends JpaRepository<Leg, Integer> {
    Leg findById(int id);

    ArrayList<Leg> findByJourney(Journey journey);

    ArrayList<Leg> findLegsByPassengersContaining(User user);

    @Query(value = "SELECT count(*), l.end_address from leg l group by (l.end_address) ORDER BY 1 DESC LIMIT 5", nativeQuery = true)
    ArrayList<Object[]> getTopFiveDestinations();

}
