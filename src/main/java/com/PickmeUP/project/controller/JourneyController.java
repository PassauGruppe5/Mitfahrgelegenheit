package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Car;
import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JourneyController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private LegService legService;

    @Autowired
    private RepeatService repeatService;

    @Autowired
    private CarService carService;

    @RequestMapping(value = "/journey/create", method = RequestMethod.GET)
    public ModelAndView ShowMap(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("/journey/create/Fahrt_anbieten");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/create", method = RequestMethod.POST)
    public ModelAndView handleJourney(@RequestBody Journey journey) throws JSONException {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Car carToSave = new Car();
        carToSave.setColour(journey.getCar().getColour());
        carToSave.setType(journey.getCar().getType());
        carToSave.setLicence(journey.getCar().getLicence());
        carService.save(carToSave);
        Journey journeyToSave = journey;
        journeyToSave.setDriver(loggedIn);
        journeyToSave.setPriceKm(journey.getPriceKm()/100);
        journeyToSave.setRepeat(repeatService.findRepeatById(journey.getRepeat().getId()));
        journeyToSave.setCar(carToSave);
        journeyService.saveJourney(journeyToSave);


        String rawJson = journey.getRoute();
        JSONObject root = new JSONObject(rawJson);
        JSONArray routesArray = root.getJSONArray("routes");
        JSONObject summary = routesArray.getJSONObject(0);
        JSONArray legs = summary.getJSONArray("legs");
        List<Leg> legsToSave = new ArrayList<>();

            for(int i=0; i < legs.length(); i++ ){

                JSONObject jsonLegNormal = legs.getJSONObject(i);
                JSONObject duration = jsonLegNormal.getJSONObject("duration");
                JSONObject distance = jsonLegNormal.getJSONObject("distance");
                JSONObject end_location = jsonLegNormal.getJSONObject("end_location");
                JSONObject start_location = jsonLegNormal.getJSONObject("start_location");
                Leg leg = new Leg();
                leg.setPosition(i+1);
                leg.setStart_address(jsonLegNormal.getString("start_address"));
                leg.setEnd_address(jsonLegNormal.getString("end_address"));
                leg.setDistance(distance.getInt("value"));
                leg.setDuration(duration.getInt("value"));
                leg.setJourney(journey);
                leg.setBags(journey.getBags());
                leg.setSeats(journey.getSeats());
                legService.saveLeg(leg);
                legsToSave.add(leg);

            }
        journeyToSave.setLegsInJourney(legsToSave);
        journeyService.saveJourney(journeyToSave);
        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/list/show", method = RequestMethod.GET)
    public ModelAndView handleSearchResult(@RequestBody Journey journey) throws JSONException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/journey/list/show/Angeboten_menue");
        return modelAndView;
    }
}
