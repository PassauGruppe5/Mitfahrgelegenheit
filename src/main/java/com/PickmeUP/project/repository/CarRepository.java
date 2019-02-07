package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    //      finds Car by id.
    //
    //      @param id                 id to be looked for.
    //      @return Car               the Car.
    Car findById(int id);
}