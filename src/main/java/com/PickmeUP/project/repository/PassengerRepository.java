package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Passenger findById(int id);
}