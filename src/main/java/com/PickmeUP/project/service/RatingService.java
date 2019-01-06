package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Rating;
import com.PickmeUP.project.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void saveRating(Rating rating){ratingRepository.save(rating);}
}
