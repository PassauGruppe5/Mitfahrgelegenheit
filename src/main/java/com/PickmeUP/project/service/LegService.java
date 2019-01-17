package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LegService {

    @Autowired
    private LegRepository legRepository;

    public void saveLeg(Leg leg) { legRepository.save(leg); }

    public ArrayList<Leg> findByJourney(Journey journey){return legRepository.findByJourney(journey);}
}