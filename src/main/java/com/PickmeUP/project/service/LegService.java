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

    public Leg findById(int id){return legRepository.findById(id);}

    public void saveLeg(Leg leg) { legRepository.save(leg); }

    public ArrayList<Leg> findByJourney(Journey journey){return legRepository.findByJourney(journey);}

    public ArrayList<Leg> findLegsByPassengersContaining(User passenger){ return legRepository.findLegsByPassengersContaining(passenger);}

    public ArrayList<Object[]> topOfALl(){return legRepository.topOfALl();}

    public ArrayList<Object[]> findTopPassengers(){return legRepository.findTopPassengers();}
 }