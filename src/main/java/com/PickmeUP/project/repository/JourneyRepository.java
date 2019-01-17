package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {
    Journey findById(int id);

    List<Journey> findByDriver(User driver);

    @Query("SELECT j FROM Journey j WHERE j.id IN (SELECT s.journey FROM Leg s JOIN Leg e ON e.journey = s.journey WHERE s.start_address LIKE %:von% AND e.end_address LIKE %:nach% )")
    ArrayList<Journey> findJourneysByPossibleRoute(String von, String nach);

}