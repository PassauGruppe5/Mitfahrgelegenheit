package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Rating;
import com.PickmeUP.project.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserService userService;

    //      save Rating.
    //
    //      @param  rating              rating to be saved.
    //      @return void
    public void saveRating(Rating rating){ratingRepository.save(rating);}

    //      find ratings by receiver
    //
    //      @param  id                   receiver to look for.
    //      @return List<Rating>         result ratings.
    public List<Rating> getRatingsOfUser(int id){return ratingRepository.findByReceiver(userService.findUserById(id));}
}
