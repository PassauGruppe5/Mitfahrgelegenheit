package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Booking;
import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking findById(int id){return bookingRepository.findById(id);}

    public Booking save(Booking booking){ booking.setActive(1); return bookingRepository.save(booking);}

    public Booking update(Booking booking){return bookingRepository.save(booking);}

    public Booking findByJourneyAndActive(Journey journey,int active){return bookingRepository.findByJourneyAndActive(journey,active);}
}
