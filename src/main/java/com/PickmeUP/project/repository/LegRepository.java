package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LegRepository extends JpaRepository<Leg, Integer> {

    //      finds Leg by id.
    //
    //      @param id                 id to be looked for.
    //      @return Leg               the Leg.
    Leg findById(int id);

    //      finds Leg by journey.
    //
    //      @param journey                 journey to be looked for.
    //      @return ArrayList<Leg>         the result Leg.
    ArrayList<Leg> findByJourney(Journey journey);

    //      finds Leg by passenger.
    //
    //      @param user                    user to be looked for.
    //      @return ArrayList<Leg>         the result Leg.
    ArrayList<Leg> findLegsByPassengersContaining(User user);

    //      finds top five most found end_addresses of legs.
    //
    //      @return ArrayList<Object[]>         the result Legs.
    @Query(value = "SELECT COUNT(l.id),l.end_address FROM leg AS l WHERE l.journey IN" +
                    "(SELECT j.id FROM journey AS j WHERE j.canceled != 1)" +
                    "GROUP BY end_address ORDER BY count(l.id) DESC LIMIT 5", nativeQuery = true)
    ArrayList<Object[]> topOfALl();

    //      finds top five users with the highest amount of bookings.
    //
    //      @return ArrayList<Object[]>         the result Passengers.
    @Query(value = "SELECT COUNT(*), u.name, u.last_name, u.user_id FROM leg l JOIN leg_user AS lu " +
                    "ON lu.leg_id = l.id JOIN journey AS j ON j.id = l.journey JOIN user u ON  " +
                    " lu.user_id = u.user_id WHERE j.canceled = 0 GROUP BY u.name, u.last_name, u.user_id " +
                    " ORDER BY COUNT(*) DESC LIMIT 5",nativeQuery = true)
    ArrayList<Object[]> findTopPassengers();
}
