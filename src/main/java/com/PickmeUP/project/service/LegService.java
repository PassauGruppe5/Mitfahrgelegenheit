package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LegService {

    @Autowired
    private LegRepository legRepository;

    //      finds Leg by id.
    //
    //      @param  id                  id to be looked for.
    //      @return Leg                 the Leg.
    public Leg findById(int id){return legRepository.findById(id);}

    //      saves Leg.
    //
    //      @param  leg                 leg to be saved.
    //      @return void
    public void saveLeg(Leg leg) { legRepository.save(leg); }

    //      find Legs by Journey.
    //
    //      @param  journey              journey to look for.
    //      @return ArrayList<Leg>       result legs.
    public ArrayList<Leg> findByJourney(Journey journey){return legRepository.findByJourney(journey);}

    //      find Legs by passenger.
    //
    //      @param  passenger            passenger to look for.
    //      @return ArrayList<Leg>       result legs.
    public ArrayList<Leg> findLegsByPassengersContaining(User passenger){ return legRepository.findLegsByPassengersContaining(passenger);}

    //      find top five end_addresses of legs.
    //
    //      @return ArrayList<Object>       result end_addresses.
    public ArrayList<Object[]> topOfALl(){return legRepository.topOfALl();}

    //      find top five pessangers by booked of legs.
    //
    //      @return ArrayList<Object>       top five passengers.
    public ArrayList<Object[]> findTopPassengers(){return legRepository.findTopPassengers();}
 }