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

    @Autowired
    private BookingService bookingService;


    //      Gets journey creation page.
    //
    //      @return the ModelAndView modelandview.


    @RequestMapping(value = "/journey/create", method = RequestMethod.GET)
    public ModelAndView ShowMap(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("/journey/create/Fahrt_anbieten");
        return modelAndView;
    }


    //      Handles Jounrey creation.
    //
    //      @param Journey              Journey instance formed by JSON.
    //      @return modelAndView        the ModelAndView.

    @RequestMapping(value = "/journey/create", method = RequestMethod.POST)
    public ModelAndView handleJourney(@RequestBody Journey journey) throws JSONException, ParseException {

        // get currently logged in user.
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        // parse legs from route element.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String rawJson = journey.getRoute();
        JSONObject root = new JSONObject(rawJson);
        JSONArray routesArray = root.getJSONArray("routes");
        JSONObject summary = routesArray.getJSONObject(0);
        JSONArray legs = summary.getJSONArray("legs");

        // create Car object.
        Car carToSave = new Car();
        carToSave.setColour(journey.getCar().getColour());
        carToSave.setType(journey.getCar().getType());
        carToSave.setLicence(journey.getCar().getLicence());
        carService.save(carToSave);

        //create Journey object top be saved.
        Journey journeyToSave = journey;
        journeyToSave.setDriver(loggedIn);
        journeyToSave.setPriceKm(journey.getPriceKm());
        journeyToSave.setRepeat(repeatService.findRepeatById(journey.getRepeat().getId()));
        journeyToSave.setCar(carToSave);

        Calendar calendar = new GregorianCalendar();
        Date dateOfJourney = formatter.parse(journeyToSave.getDepartureDate());
        calendar.setTime(dateOfJourney);

        //store repeats.
        int difDays = 0;
        int iterations = 0;
        LocalDate startDate = LocalDate.parse(journeyToSave.getDepartureDate());
        LocalDate endDate = LocalDate.parse(journeyToSave.getArrivalDate());

        //set interval of additional dates depending on selected repeat option.
        switch (journeyToSave.getRepeat().getId()){
            case 2: difDays = 1; iterations = 13; break;
            case 3: difDays = 7; iterations = 11; break;
            case 4: difDays = 1; iterations = 11; break;
        }

        //create needed amount of new journeys with added date depending on chosen repeat
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

                //generate a copy of the oroginal Journey Object and set new dates.
                Journey iteration = journeyService.saveAsClone(journeyToSave);

                List<Leg> legsToSave = new ArrayList<>();

                //add Leg objects to copy of Jounrey object.
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

            // send jounrey created mail.
            try {
                GmailService.sendCreatedMail(loggedIn);
            } catch (MailException e) {
                e.printStackTrace();
            }
            modelAndView.addObject("user", loggedIn);
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

    //      Gets search result page.
    //
    //      @param Journey            the Journey mimicked by Search input.
    //      @return modelAndView      the ModelAndView.
    @RequestMapping(value = "/journey/list/show", method = RequestMethod.GET)
    public ModelAndView handleSearchResult(@RequestBody Journey journey) throws JSONException {
        ModelAndView modelAndView = new ModelAndView();

        // get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/journey/list/show/Angeboten_menue");
        return modelAndView;
    }

    //      Handles offered Jounrey delete.
    //
    //      @param id                   id of Journey Object to be deleted.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/journey/cancelOffered", method = RequestMethod.POST)
    public ModelAndView handleCancelOffered(@RequestBody int id) {
        ModelAndView modelAndView = new ModelAndView();

        //get Journey object correspondint to parameter id.
        Journey journeyToCancelOffered = journeyService.findById(id);

        // get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        List<Leg> legsOfJourney = legService.findByJourney(journeyToCancelOffered);

        //empty all passengers from journey.
        for(Leg leg : legsOfJourney){
            List<User> passengersOfLeg = leg.getPassengers();
            for(User user : passengersOfLeg){

                //send jounrey changed mail to passengers.
                try {
                    GmailService.sendChangedJourneyMailPassenger(user);
                } catch (MailException e) {
                    e.printStackTrace();
                }
            }
            passengersOfLeg.clear();
            leg.setPassengers(passengersOfLeg);
            legService.saveLeg(leg);
        }

        // deactivate Journey and mark as canceled.
        journeyToCancelOffered.setActive(0);
        journeyToCancelOffered.setCanceled(1);
        journeyService.updateJourney(journeyToCancelOffered);

        // send jounrey changed mail to driver.
        try {
            GmailService.sendChangedJourneyMailDriver(loggedIn);
        } catch (MailException e) {
            e.printStackTrace();
        }

        modelAndView.addObject("stornoSuccess","True");
        modelAndView.addObject("id", loggedIn.getId());
        modelAndView.setViewName("redirect:/profile/show/profile");
        return modelAndView;
    }

    //      Handles booked Jounrey delete.
    //
    //      @param id                   id of Journey Object contained in the booking to be deleted.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/journey/cancelBooked", method = RequestMethod.POST)
    public ModelAndView handleCancelBooked(@RequestBody int id) {
        ModelAndView modelAndView = new ModelAndView();

        //get Journey object corresponding to parameter id.
        Journey journeyToCancel = journeyService.findById(id);

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get all Leg objects of selected Journey object.
        List<Leg> legsToBeCanceled = legService.findByJourney(journeyToCancel);

        //get Account object of logged in user.
        Account accountPassenger = accountService.findbyUser(loggedIn);

        //get Account object of journey's driver.
        Account accountDriver = accountService.findbyUser(journeyToCancel.getDriver());

        //get latest Booking object corresponding to journey and active status.
        Booking booking = bookingService.findByJourneyAndActive(journeyToCancel,1);

        //if logged in user is passenger of a leg, the correlation will be deleted.
        //Leg object will be updated.
        for(Leg leg : legsToBeCanceled){
            List<User> passengersOfLeg = leg.getPassengers();
            if(passengersOfLeg.contains(loggedIn)){
                passengersOfLeg.remove(loggedIn);
                legService.saveLeg(leg);
            }
        }

        //send driver a mail that a passenger canceled a booking for one of its journeys.
        try {
            GmailService.sendCancelBookedJourneyMailDriver(journeyToCancel.getDriver());
        } catch (MailException e) {
            e.printStackTrace();
        }

        //send cancel succes mail to logged in user.
        try {
            GmailService.sendCancelBookedJourneyMailPassenger(loggedIn);
        } catch (MailException e) {
            e.printStackTrace();
        }

        //add payed amou,t back to passenger account.
        accountPassenger.setBalance(accountPassenger.getBalance()+ booking.getAmount());
        accountService.updateAccount(accountPassenger);

        //substract payed amount to driver account.
        accountDriver.setBalance(accountDriver.getBalance()- booking.getAmount());
        accountService.updateAccount(accountDriver);

        //mark booking as canceled.
        booking.setActive(0);
        bookingService.update(booking);

        modelAndView.addObject("id", loggedIn.getId());
        modelAndView.setViewName("redirect:/profile/show/profile");
        return modelAndView;
    }

    //      Handles display jounrey details.
    //
    //      @param id                   id of Journey Object.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/journey/details", method = RequestMethod.GET)
    public ModelAndView handleDeatils(@RequestParam int id){
        ModelAndView modelAndView = new ModelAndView();

        //get currenlty logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get Jounrey object corresponding to parameter id.
        Journey journey = journeyService.findById(id);

        //get all legs of Journey object.
        ArrayList<Leg> legs = legService.findByJourney(journey);

        //facilitate display of empty legs depending on amount of legs.
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

    //      Handles display of journey edit page..
    //
    //      @param id                   id of Journey object.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/journey/edit", method = RequestMethod.GET)
    public ModelAndView handleEdit(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get Journey object corresponding to parameter id.
        Journey journey = journeyService.findById(id);

        //get all Legs of journey.
        ArrayList<Leg> legs = legService.findByJourney(journey);

        //facilitate display of empty legs depending on amount of legs.
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

        //check if editor is driver of the journey.
        //if false, then show details instead.
        //if true, then show edit page.
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

    //      Handles edit of journey objects.
    //
    //      @param id                   id of Journey Object.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/journey/edit", method = RequestMethod.POST)
    public ModelAndView handleEdit(@RequestBody Journey journey) {
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get Journey object corresponding to id of received object journey.
        Journey journeyToUpdate = journeyService.findById(journey.getId());

        //get car of journey.
        Car carofJourney = journeyToUpdate.getCar();

        //set car to input values and safe it.
        carofJourney.setType(journey.getCar().getType());
        carofJourney.setColour(journey.getCar().getColour());
        carofJourney.setLicence(journey.getCar().getLicence());
        carService.save(carofJourney);

        //set values of journey to edited input values and safe it.
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
