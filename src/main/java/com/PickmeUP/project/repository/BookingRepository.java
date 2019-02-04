package com.PickmeUP.project.repository;


import com.PickmeUP.project.model.Booking;
import com.PickmeUP.project.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findById(int bonus_id);

    Booking findByJourneyAndActive(Journey journey, int active);
}
