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

    //      finds Journey by id.
    //
    //      @param id                      id to be looked for.
    //      @return Journey                 the Journey.
    public Journey findById(int id){return journeyRepository.findById(id);}

    //      saves Journey.
    //      sets initial active status to 1 (active)
    //
    //      @param  journey             journey to be saved.
    //      @return void
    public void saveJourney(Journey journey){
        journey.setActive(1);
        journeyRepository.save(journey);
    }

    //      finds Journey by driver and active status.
    //
    //      @param driver                  driver to be looked for.
    //      @param active                  active status to be looked for.
    //      @return List<Journey>          the result Journeys.
    public List<Journey> findByDriverAndActive(User driver, int active) {return journeyRepository.findByDriverAndActiveOrderByDepartureDateAscDapartureTimeAsc(driver,active);}

    //      updates Journey.
    //
    //      @param  journey             journey to be saved.
    //      @return void
    public void updateJourney(Journey journey){journeyRepository.save(journey);
    }

    //      finds Journeys by containing legs.
    //
    //      @param legsOfPassenger              Legs to be looked for.
    //      @return ArrayList<Journey>          the result Journeys.
    public ArrayList<Journey> findJourneysByLegs(ArrayList<Leg> legsOfPassenger){
        ArrayList<Integer> journeysOfPassenger = new ArrayList<>();
        for(Leg leg : legsOfPassenger){
            if(journeysOfPassenger.contains(leg.getJourney()) == false) {
                journeysOfPassenger.add(leg.getJourney().getId());
            }
        }

        return journeyRepository.findJourneysByIdIn(journeysOfPassenger);

        }

    //      saves clone of Jounrey.
    //
    //      @param startJourney                 journey tpo be cloned.
    //      @return Jounrey                     the cloned and saved Journeys.
    public Journey saveAsClone(Journey startJourney){
        Journey clone = new Journey();
        clone.clone(startJourney);
        journeyRepository.save(clone);
        return clone;
        }

    //      find all journeys.
    //
    //      @return List<Journey>                all journeys.
    public List<Journey> findAllJourney(){return journeyRepository.findAll();}

    //      finds all clones of a journey.
    //
    //      @param journeyToGetClonesFrom        journey to look for clones of.
    //      @return ArrayList<Journey>           all clones.
    public ArrayList<Journey> findAllClonedJourneys(Journey journeyToGetClonesFrom){
        ArrayList<Journey> arrayOfClones= new ArrayList<>();
        for(Journey journey : this.findAllJourney()) {
            if(journey.checkForClone(journeyToGetClonesFrom)){
                arrayOfClones.add(journey);
            }
        }

        return arrayOfClones;
        }

    //      finds all journeys by active status and canceled status.
    //
    //      @param active                        active status to look for clones of.
    //      @param canceled                      canceled status to look for clones of.
    //      @return ArrayList<Journey>           all clones.
    public ArrayList<Journey> findAllByActiveAndCanceled(int active, int canceled){return journeyRepository.findAllByActiveAndCanceled(active, canceled);}

    //      top 5 users by most journeys offered.
    //
    //      @return ArrayList<Object[]>           top five drivers.
    public ArrayList<Object[]> findTopDrivers(){return journeyRepository.findTopDrivers();}

}
