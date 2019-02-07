package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {

    //      finds Journey by id.
    //
    //      @param id                 id to be looked for.
    //      @return Journey           the Journey.
    Journey findById(int id);

    //      finds all Journeys by driver and active status.
    //      Ordered by departureDate and departureTime ascending.
    //
    //      @param driver                   driver to be looked for.
    //      @param active                   active status to be looked for.
    //      @return List<Journey>           the result Journeys.
    List<Journey> findByDriverAndActiveOrderByDepartureDateAscDapartureTimeAsc(User driver, int active);

    //      finds all Journeys.
    //
    //      @return List<Journey>           the result Journeys.
    List<Journey> findAll();

    //      finds all Journeys containing legs that match start_address and end_address.
    //      match finding via LIKE.
    //
    //      @param von                           start_address to be looked for.
    //      @param nach                          end_address to be looked for.
    //      @return ArrayList<Journey>           the result Journeys.
    @Query("SELECT j FROM Journey j WHERE j.id IN (SELECT s.journey FROM Leg s JOIN Leg e ON e.journey = s.journey WHERE s.start_address LIKE %:von% AND e.end_address LIKE %:nach% )")
    ArrayList<Journey> findJourneysByPossibleRoute(String von, String nach);

    //      finds all Journeys by list of ids.
    //
    //      @param id                            list of ids to be looked for.
    //      @return ArrayList<Journey>           the result Journeys.
    ArrayList<Journey> findJourneysByIdIn(ArrayList<Integer> id);

    //      finds all Journeys by active status and canceled status.
    //
    //      @param active                        active status to be looked for.
    //      @param canceled                      canceled status of ids to be looked for.
    //      @return ArrayList<Journey>           the result Journeys.
    ArrayList<Journey> findAllByActiveAndCanceled(int active, int canceled);

    //      finds all top five users with most journeys offered.
    //
    //      @return ArrayList<Object[]>         the result Users.
    @Query(value = "SELECT  count(*),user.name, user.last_name, user.user_id from journey JOIN " +
                    "user on user.user_id = journey.driver WHERE journey.canceled = 0 GROUP BY driver ORDER BY" +
                    " count(*) DESC LIMIT 5",nativeQuery = true)
    ArrayList<Object[]> findTopDrivers();


}