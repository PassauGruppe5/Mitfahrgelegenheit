package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Journey findById(int id){return journeyRepository.findById(id);}

    public void saveJourney(Journey journey){
        journey.setActive(1);
        journeyRepository.save(journey);
    }

    public List<Journey> findByDriverAndActive(User driver, int active) {return journeyRepository.findByDriverAndActive(driver,active);}

    public void updateJourney(Journey journey){journeyRepository.save(journey);
    }

    public ArrayList<Journey> findJourneysByLegs(ArrayList<Leg> legsOfPassenger){
        ArrayList<Integer> journeysOfPassenger = new ArrayList<>();
        for(Leg leg : legsOfPassenger){
            if(journeysOfPassenger.contains(leg.getJourney()) == false) {
                journeysOfPassenger.add(leg.getJourney().getId());
            }
        }

        return journeyRepository.findJourneysByIdIn(journeysOfPassenger);

        }

        public Journey saveAsClone(Journey startJourney){
        Journey clone = new Journey();
        clone.clone(startJourney);
        journeyRepository.save(clone);
        return clone;
        }

        public List<Journey> findAllJourney(){return journeyRepository.findAll();}

        public ArrayList<Journey> findAllClonedJourneys(Journey journeyToGetClonesFrom){
        ArrayList<Journey> arrayOfClones= new ArrayList<>();
        for(Journey journey : this.findAllJourney()) {
            if(journey.checkForClone(journeyToGetClonesFrom)){
                arrayOfClones.add(journey);
            }
        }
            return arrayOfClones;
        }

        public ArrayList<Journey> findAllByActiveAndCanceled(int active, int canceled){return journeyRepository.findAllByActiveAndCanceled(active, canceled);}
}
