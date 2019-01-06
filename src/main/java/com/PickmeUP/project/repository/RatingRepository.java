package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Rating;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findById(int id);

    List<Rating> findByReceiver(User reseiver);
}