package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public void saveJourney(Journey journey){journeyRepository.save(journey);}

    public List<Journey> findByDriver(User driver) {return journeyRepository.findByDriver(driver);}

    public void updateJourney(Journey journey){
        journeyRepository.save(journey);
    }
}
