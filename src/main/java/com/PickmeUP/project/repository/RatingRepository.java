package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findById(int id);
}