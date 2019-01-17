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

    public void saveJourney(Journey journey){
        journey.setActive(1);
        journeyRepository.save(journey);
    }

    public List<Journey> findByDriverAndActive(User driver, int active) {return journeyRepository.findByDriverAndActive(driver,active);}

    public void updateJourney(Journey journey){journeyRepository.save(journey);
    }
}
