package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.JourneyRepository;
import com.PickmeUP.project.service.LegService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private LegService legService;

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user != null){
            modelAndView.addObject("user", user);
            modelAndView.setViewName("Home_Angemeldet");
        }
        else {
            modelAndView.setViewName("Home_Unangemeldet");
        }

        return modelAndView;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public ModelAndView handleSearch(@RequestParam String von,@RequestParam String nach,@RequestParam String datum,@RequestParam String zeit ) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Journey> journeys = journeyRepository.findJourneysByPossibleRoute(von,nach);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        ArrayList<Journey> results = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalTime searchTime = LocalTime.parse(zeit);
        Date searchDate = formatter.parse(datum);

        if(loggedIn == null){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        for (Journey journey: journeys) {
            ArrayList<Leg> legs = legService.findByJourney(journey);

            Boolean in_trip = false;
            Boolean genug_platz = false;

            for (Leg leg : legs) {
                if(leg.getStart_address().contains(von)){
                    in_trip = true;
                }
                if(in_trip){
                    if(leg.checkSpace() == false){
                        genug_platz = false;
                        modelAndView.addObject("errorMessage","Es wurden keine Fahrten zu den Eingaben gefunend.");
                        break;
                    }
                    genug_platz = true;
                }
                if(leg.getEnd_address().contains(nach) && in_trip){
                    break;
                }
            }

            if(genug_platz){

               if(journey.checkDate(searchDate) && journey.checkTime(searchTime) && journey.getActive() == 1 && journey.getCanceled() == 0){

                    journey.setOrigin(journey.getOrigin().replaceFirst(", Deutschland",""));
                    journey.setArrivalTime(journey.getArrivalTime().substring(0,5));
                    journey.setDepartureTime(journey.getDepartureTime().substring(0,5));
                    journey.setDepartureDate(journey.getDepartureDate().substring(0,10));
                    journey.setArrivalDate(journey.getArrivalDate().substring(0,10));
                    journey.setDestination(journey.getDestination().replaceFirst(", Deutschland",""));
                    results.add(journey);
               }
            }
        }


        modelAndView.addObject("results",results);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/journey/list/show/Angeboten_menue");
        return modelAndView;
    }
}
