package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.*;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private AccountService accountService;

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
        journeyToSave.setPriceKm(journey.getPriceKm());
        journeyToSave.setRepeat(repeatService.findRepeatById(journey.getRepeat().getId()));
        journeyToSave.setCar(carToSave);

        Calendar calendar = new GregorianCalendar();
        Date dateOfJourney = formatter.parse(journeyToSave.getDepartureDate());
        calendar.setTime(dateOfJourney);

        int difDays = 0;
        int iterations = 0;
        LocalDate startDate = LocalDate.parse(journeyToSave.getDepartureDate());
        LocalDate endDate = LocalDate.parse(journeyToSave.getArrivalDate());

        switch (journeyToSave.getRepeat().getId()){
            case 2: difDays = 1; iterations = 13; break;
            case 3: difDays = 7; iterations = 11; break;
            case 4: difDays = 1; iterations = 11; break;
        }

        for(int j = iterations; j >=0  ;j--){

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
                    Leg leg = new Leg();
                    legService.saveLeg(leg);
                    leg.setPosition(i + 1);
                    leg.correctAddresses(jsonLegNormal.getString("start_address"), jsonLegNormal.getString("end_address"));
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

    @RequestMapping(value = "/journey/cancelOffered", method = RequestMethod.POST)
    public ModelAndView handleCancelOffered(@RequestBody int id) {
        ModelAndView modelAndView = new ModelAndView();
        Journey journeyToCancelOffered = journeyService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        List<Leg> legsOfJourney = legService.findByJourney(journeyToCancelOffered);

        for(Leg leg : legsOfJourney){
            List<User> passengersOfLeg = leg.getPassengers();
            passengersOfLeg.clear();
            leg.setPassengers(passengersOfLeg);
            legService.saveLeg(leg);
        }

        journeyToCancelOffered.setActive(0);
        journeyToCancelOffered.setCanceled(1);
        journeyService.updateJourney(journeyToCancelOffered);

        modelAndView.addObject("id", loggedIn.getId());
        modelAndView.setViewName("redirect:/profile/show/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/cancelBooked", method = RequestMethod.POST)
    public ModelAndView handleCancelBooked(@RequestBody int id) {
        ModelAndView modelAndView = new ModelAndView();
        Journey journeyToCancel = journeyService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        List<Leg> legsToBeCanceled = legService.findByJourney(journeyToCancel);
        Account accountPassenger = accountService.findbyUser(loggedIn);
        Account accountDriver = accountService.findbyUser(journeyToCancel.getDriver());

        for(Leg leg : legsToBeCanceled){
            List<User> passengersOfLeg = leg.getPassengers();
            if(passengersOfLeg.contains(loggedIn)){
                double balanceRefund = journeyToCancel.getPriceKm() * leg.getDistance()/1000;
                accountPassenger.setBalance(accountPassenger.getBalance()+balanceRefund);
                accountService.updateAccount(accountPassenger);
                accountDriver.setBalance(accountDriver.getBalance()-balanceRefund);
                accountService.updateAccount(accountDriver);
                passengersOfLeg.remove(loggedIn);
                legService.saveLeg(leg);
            }
        }

        modelAndView.addObject("id", loggedIn.getId());
        modelAndView.setViewName("redirect:/profile/show/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/details", method = RequestMethod.GET)
    public ModelAndView handleDeatils(@RequestParam int id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journey = journeyService.findById(id);
        ArrayList<Leg> legs = legService.findByJourney(journey);
        if(legs.size() == 3){
            Leg first = legs.get(1);
            Leg second = legs.get(2);
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }
        if(legs.size() == 2) {
            Leg first = legs.get(1);
            Leg second = legs.get(0);
            second.setStart_address("");
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }
        if(legs.size() == 1){
            Leg first = legs.get(0);
            first.setStart_address("");
            Leg second = legs.get(0);
            second.setStart_address("");
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }

        modelAndView.addObject("user",loggedIn);
        modelAndView.addObject("journey",journey);
        modelAndView.setViewName("/journey/show/Details");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/edit", method = RequestMethod.GET)
    public ModelAndView handleEdit(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journey = journeyService.findById(id);
        ArrayList<Leg> legs = legService.findByJourney(journey);
        if(legs.size() == 3){
            Leg first = legs.get(1);
            Leg second = legs.get(2);
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }
        if(legs.size() == 2) {
            Leg first = legs.get(1);
            Leg second = legs.get(0);
            second.setStart_address("");
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }
        if(legs.size() == 1){
            Leg first = legs.get(0);
            first.setStart_address("");
            Leg second = legs.get(0);
            second.setStart_address("");
            modelAndView.addObject("leg1",first);
            modelAndView.addObject("leg2",second);
        }

        if (loggedIn != journey.getDriver()){
            modelAndView.addObject("user",loggedIn);
            modelAndView.addObject("journey",journey);
            modelAndView.setViewName("/journey/show/Details");
            return modelAndView;
        }

        modelAndView.addObject("user",loggedIn);
        modelAndView.addObject("journey",journey);
        modelAndView.setViewName("/journey/create/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/journey/edit", method = RequestMethod.POST)
    public ModelAndView handleEdit(@RequestBody Journey journey) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journeyToUpdate = journeyService.findById(journey.getId());
        Car carofJourney = journeyToUpdate.getCar();

        carofJourney.setType(journey.getCar().getType());
        carofJourney.setColour(journey.getCar().getColour());
        carofJourney.setLicence(journey.getCar().getLicence());

        carService.save(carofJourney);

        journeyToUpdate.setCar(carofJourney);
        journeyToUpdate.setSeats(journey.getSeats());
        journeyToUpdate.setPriceKm(journey.getPriceKm());
        journeyToUpdate.setBags(journey.getBags());
        journeyToUpdate.setPriceBag(journey.getPriceBag());

        journeyService.updateJourney(journeyToUpdate);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/journey/create/edit");
        return modelAndView;
    }
}
