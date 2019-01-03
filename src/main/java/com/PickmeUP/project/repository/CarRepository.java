package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car findById(int id);
}