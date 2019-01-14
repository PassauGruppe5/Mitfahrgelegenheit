package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegService {

    @Autowired
    private LegRepository legRepository;

    public void saveLeg(Leg leg) { legRepository.save(leg); }
}