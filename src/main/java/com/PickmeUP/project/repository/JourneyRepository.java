package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {
    Journey findById(int id);

    List<Journey> findByDriverAndActive(User driver,int active);

    List<Journey> findAll();

    @Query("SELECT j FROM Journey j WHERE j.id IN (SELECT s.journey FROM Leg s JOIN Leg e ON e.journey = s.journey WHERE s.start_address LIKE %:von% AND e.end_address LIKE %:nach% )")
    ArrayList<Journey> findJourneysByPossibleRoute(String von, String nach);

    ArrayList<Journey> findJourneysByIdIn(ArrayList<Integer> id);

    ArrayList<Journey> findAllByActiveAndCanceled(int active, int canceled);

    @Query(value = "SELECT  count(*),user.name, user.last_name, user.user_id from journey JOIN " +
                    "user on user.user_id = journey.driver WHERE journey.canceled != 0 GROUP BY driver ORDER BY" +
                    " count(*) DESC LIMIT 5",nativeQuery = true)
    ArrayList<Object[]> findTopDrivers();


}