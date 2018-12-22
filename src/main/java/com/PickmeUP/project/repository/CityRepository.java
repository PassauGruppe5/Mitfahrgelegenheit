package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    City findById(int id);
}