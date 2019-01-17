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
    public ModelAndView handleSearch(@RequestParam String von,@RequestParam String nach,@RequestParam String datum) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Journey> journeys = journeyRepository.findJourneysByPossibleRoute(von,nach);
        ArrayList<Journey> results = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date searchDate = formatter.parse(datum);

        for (Journey journey: journeys) {
            ArrayList<Leg> legs = legService.findByJourney(journey);

            Boolean in_trip = false;
            Boolean genug_platz = false;

            for (Leg leg : legs) {
                if(leg.getStart_address().contains(von)){
                    in_trip = true;
                }
                if(in_trip){
                    genug_platz = true;
                }
                if(leg.getEnd_address().contains(nach) && in_trip){
                    break;
                }
            }

            if(genug_platz){
               if(journey.checkDate(searchDate)){
                    results.add(journey);
               }
            }
        }


        modelAndView.addObject("results",results);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
