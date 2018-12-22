package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findById(int id);
}