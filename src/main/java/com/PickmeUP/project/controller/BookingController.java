package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.LegService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private LegService legService;

    @RequestMapping(value = "/booking/create", method = RequestMethod.GET)
    public ModelAndView showBookingPage(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journey = journeyService.findById(id);
        User driver = journey.getDriver();
            journey.setOrigin(journey.getOrigin().replaceFirst(", Deutschland",""));
            journey.setDestination(journey.getDestination().replaceFirst(", Deutschland",""));
        ArrayList<Leg> legs = legService.findByJourney(journey);

        modelAndView.addObject("legs",legs);
        modelAndView.addObject("driver",driver);
        modelAndView.addObject("journey",journey);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/booking/create/Buchung_anlegen");
        return modelAndView;
    }

    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    public ModelAndView createBooking(@RequestBody ArrayList<Leg> selectedLegs){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        Boolean genug_platz = true;

        ArrayList<Leg> legsOnDb = new ArrayList<>();

        for(Leg leg : selectedLegs){
            legsOnDb.add(legService.findById(leg.getId()));
            if(legService.findById(leg.getId()).checkSpace() == false){
                genug_platz = false;

            }
        }

        if(genug_platz == true){
            for(Leg leg : legsOnDb){
                leg.getPassengers().add(loggedIn);
                legService.saveLeg(leg);
            }
            modelAndView.addObject("succesMessage","Erfolgreich zur Fahrt hinzugebucht.");
        }
        else{
            modelAndView.addObject("errorMessage","Da war wohl jemand schnelle. Alle Plätze sind bereits vergeben.");
        }

        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
