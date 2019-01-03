package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Passenger;
import com.PickmeUP.project.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;
    private Passenger passenger = new Passenger();
}
