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

    //      finds Booking by id.
    //
    //      @param id                      id to be looked for.
    //      @return Booking               the Booking.
    public Booking findById(int id){return bookingRepository.findById(id);}

    //      saves Booking by id.
    //
    //      @param  booking             booking to be saved.
    //      @return Booking             the Booking.
    public Booking save(Booking booking){ booking.setActive(1); return bookingRepository.save(booking);}

    //      update Booking by id.
    //
    //      @param  booking             booking to be updated.
    //      @return Booking             the Booking.
    public Booking update(Booking booking){return bookingRepository.save(booking);}

    //      finds Booking by jounrey and active status.
    //
    //      @param journey                journey to be looked for.
    //      @param active                 active statusto be looked for.
    //      @return Booking               the Booking.
    public Booking findByJourneyAndActive(Journey journey,int active){return bookingRepository.findByJourneyAndActive(journey,active);}
}
