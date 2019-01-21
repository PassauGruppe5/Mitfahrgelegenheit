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
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public ModelAndView handleJourney(@RequestBody Journey journey) throws JSONException, ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String rawJson = journey.getRoute();
        JSONObject root = new JSONObject(rawJson);
        JSONArray routesArray = root.getJSONArray("routes");
        JSONObject summary = routesArray.getJSONObject(0);
        JSONArray legs = summary.getJSONArray("legs");

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

        Calendar calendar = new GregorianCalendar();
        Date dateOfJourney = formatter.parse(journeyToSave.getDepartureDate());
        calendar.setTime(dateOfJourney);
        System.out.println("Week number:" + calendar.get(Calendar.WEEK_OF_YEAR));

        int difDays = 0;
        int iterations = 1;
        LocalDate startDate = LocalDate.parse(journeyToSave.getDepartureDate());
        LocalDate endDate = LocalDate.parse(journeyToSave.getArrivalDate());

        switch (journeyToSave.getRepeat().getId()){
            case 2: difDays = 1; iterations = 14; break;
            case 3: difDays = 7; iterations = 12; break;
            case 4: difDays = 1; iterations = 12; break;
        }

        for(int j = iterations; j > 0 ;j--){
            if(journeyToSave.getRepeat().getId() == 4) {
                journeyToSave.setDepartureDate(startDate.plus(difDays*j, ChronoUnit.MONTHS).toString());
                journeyToSave.setArrivalDate(endDate.plus(difDays*j,ChronoUnit.MONTHS).toString());
            }
            else{
                journeyToSave.setDepartureDate(startDate.plus(difDays*j, ChronoUnit.DAYS).toString());
                journeyToSave.setArrivalDate(endDate.plus(difDays*j,ChronoUnit.DAYS).toString());
            }
                journey.setroute("");

                Journey iteration = journeyService.saveAsClone(journeyToSave);

                List<Leg> legsToSave = new ArrayList<>();

                for (int i = 0; i < legs.length(); i++) {

                    JSONObject jsonLegNormal = legs.getJSONObject(i);
                    JSONObject duration = jsonLegNormal.getJSONObject("duration");
                    JSONObject distance = jsonLegNormal.getJSONObject("distance");
                    JSONObject end_location = jsonLegNormal.getJSONObject("end_location");
                    JSONObject start_location = jsonLegNormal.getJSONObject("start_location");
                    Leg leg = new Leg();
                    legService.saveLeg(leg);
                    leg.setPosition(i + 1);
                    leg.setStart_address(jsonLegNormal.getString("start_address"));
                    leg.setEnd_address(jsonLegNormal.getString("end_address"));
                    leg.setDistance(distance.getInt("value"));
                    leg.setDuration(duration.getInt("value"));
                    leg.setJourney(iteration);
                    leg.setBags(iteration.getBags());
                    leg.setSeats(iteration.getSeats());
                    legService.saveLeg(leg);
                    legsToSave.add(leg);

                }
                iteration.setLegsInJourney(legsToSave);
            }


            try {
                GmailService.sendCreatedMail(loggedIn);
            } catch (MailException e) {
                e.printStackTrace();
            }
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
