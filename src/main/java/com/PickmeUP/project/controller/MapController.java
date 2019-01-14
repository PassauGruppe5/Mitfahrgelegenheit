package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.LegService;
import com.PickmeUP.project.service.UserService;
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

@Controller
public class MapController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private LegService legService;

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ModelAndView ShowMap(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("/profile/create/Fahrt_anbieten");
        return modelAndView;
    }

    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public ModelAndView handleJourney(@RequestBody Journey journey) throws JSONException {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journeyToSave = journey;
        journeyToSave.setDriver(loggedIn);
        journeyService.saveJourney(journeyToSave);

        String rawJson = journey.getRoute();
        JSONObject root = new JSONObject(rawJson);
        JSONArray routesArray = root.getJSONArray("routes");
        JSONObject summary = routesArray.getJSONObject(0);
        JSONArray legs = summary.getJSONArray("legs");

            for(int i=0; i < legs.length(); i++ ){

                JSONObject jsonLegNormal = legs.getJSONObject(i);
                JSONObject duration = jsonLegNormal.getJSONObject("duration");
                JSONObject distance = jsonLegNormal.getJSONObject("distance");
                Leg leg = new Leg();
                leg.setStart_address(jsonLegNormal.getString("start_address"));
                leg.setEnd_address(jsonLegNormal.getString("end_address"));
                leg.setDistance(distance.getInt("value"));
                leg.setDuration(duration.getInt("value"));
                leg.setJourney(journey);
                leg.setBags(journey.getBags());
                leg.setSeats(journey.getSeats());
                legService.saveLeg(leg);

                int cummulatedDuration = duration.getInt("value");
                int cummulatedDistance = distance.getInt("value");

                    for(int j = i+1 ; j < legs.length(); j++){

                        JSONObject jsonLegConnected = legs.getJSONObject(j);
                        JSONObject durationConnected = jsonLegConnected.getJSONObject("duration");
                        JSONObject distanceConnected = jsonLegConnected.getJSONObject("distance");

                        cummulatedDistance = cummulatedDistance + distanceConnected.getInt("value");
                        cummulatedDuration = cummulatedDuration+durationConnected.getInt("value");

                        Leg legConnected = new Leg();
                        legConnected.setStart_address(jsonLegNormal.getString("start_address"));
                        legConnected.setEnd_address(jsonLegConnected.getString("end_address"));

                        legConnected.setDistance(cummulatedDistance);
                        legConnected.setDuration(cummulatedDuration);
                        legConnected.setJourney(journey);
                        legConnected.setSeats(journey.getSeats());
                        legConnected.setBags(journey.getBags());

                        legService.saveLeg(legConnected);
                    }
            }

        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("Home_Angemeldet");
        return modelAndView;
    }
}
