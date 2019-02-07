package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Rating;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    //      finds Rating by id.
    //
    //      @param id                 id to be looked for.
    //      @return Rating             the Rating.
    Rating findById(int id);

    //      finds all Ratings by receiver.
    //
    //      @param receiver            User to be looked for.
    //      @return List<Rating>       the result Ratings.
    List<Rating> findByReceiver(User receiver);
}