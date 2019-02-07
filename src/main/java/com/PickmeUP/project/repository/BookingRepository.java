package com.PickmeUP.project.repository;


import com.PickmeUP.project.model.Booking;
import com.PickmeUP.project.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    //      finds Booking by id.
    //
    //      @param bonus_id               id to be looked for.
    //      @return Booking               the Bonus.
    Booking findById(int bonus_id);

    //      finds Booking by journey and active status.
    //
    //      @param journey               journey to be looked for.
    //      @param active                active to be looked for.
    //      @return Booking
    Booking findByJourneyAndActive(Journey journey, int active);
}
