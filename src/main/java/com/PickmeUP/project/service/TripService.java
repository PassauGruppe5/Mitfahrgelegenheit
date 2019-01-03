package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Trip;
import com.PickmeUP.project.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class TripService {

    @Autowired
    private TripRepository tripRepository;
    private Trip trip = new Trip();
}
