package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.*;
import com.PickmeUP.project.service.*;
import org.apache.commons.math3.util.Precision;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private LegService legService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private BookingService bookingService;

    //      handles Booking creation GET.
    //      alters appearence of addresses and presents html page.
    //
    //      @param id                   id of Journey object to place a booking for.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/booking/create", method = RequestMethod.GET)
    public ModelAndView showBookingPage(@RequestParam int id) {

        //get currently logged in User to be used as reference for the booking object.
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get Journey entity corresponding to parameter id.
        Journey journey = journeyService.findById(id);

        //get driver attribute of the Journey object.
        User driver = journey.getDriver();

        //get driver attribute of the Journey object.
        journey.setOrigin(journey.getOrigin().replaceFirst(", Deutschland",""));
        journey.setDestination(journey.getDestination().replaceFirst(", Deutschland",""));

        //get all legs within Object journey.
        ArrayList<Leg> legs = legService.findByJourney(journey);

        //get Account object of logged in User.
        Account account = accountService.findbyUser(loggedIn);

        //trim values of leg's start_address and end_address.
            for(Leg leg : legs){
                leg.correctAddresses(leg.getStart_address(), leg.getEnd_address() );
            }

        //add objects to modelAndView for presentation-
        modelAndView.addObject("account", account);
        modelAndView.addObject("legs",legs);
        modelAndView.addObject("driver",driver);
        modelAndView.addObject("journey",journey);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/booking/create/Buchung_anlegen");
        return modelAndView;
    }

    //      handles Booking creation POST.
    //      saves newly genereated booking object on database with input values.
    //
    //      @param selectedLegs         ArrayList of all legs selected to be booked.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    public ModelAndView createBooking(@RequestBody ArrayList<Leg> selectedLegs) throws ParseException {

        //get currently logged in User to be used as reference for the booking object.
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //find Journey object of legs to be booked.
        Journey journey = legService.findById(selectedLegs.get(0).getId()).getJourney();

        //get Account object of logged in user.
        Account userAccount = accountService.findbyUser(loggedIn);

        //get Account object of the journey#s driver.
        Account driverAccount = accountService.findbyUser(journey.getDriver());

        //generate Transaction object to store information about money transaction.
        Transaction transaction = new Transaction();

        //generate new Booking object.
        Booking booking = new Booking(loggedIn);

        //set boundaries of bonusprograms.
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate minusOneMonth = now.minusMonths(1);

        //get all Journeys booked by logged in user.
        List<Journey> bookedJourneys = journeyService.findJourneysByLegs(legService.findLegsByPassengersContaining(loggedIn));
        System.out.println(ZonedDateTime.now());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //initiate price calculation.
        double price = 0.00;
        Boolean genug_platz = true;

        ArrayList<Leg> legsOnDb = new ArrayList<>();

        //set price to total cost of way x price.
        //check if leg can be booked depending on seats available.
        for(Leg leg : selectedLegs){
            legsOnDb.add(legService.findById(leg.getId()));
            price = Precision.round(price + journey.getPriceKm() * leg.getDistance()/1000,2);

            //genug_platz set to false if legs has no more available seats to be booked.
            if(legService.findById(leg.getId()).checkSpace() == false){
                genug_platz = false;

            }
        }

        //add total price of bags to price.
        price = price + selectedLegs.get(0).getBags() *  journey.getPriceBag();

        //find booke journeys within bonusprogram timespan.
        int initialSize = bookedJourneys.size();
        for(int h = 0; h < bookedJourneys.size(); h++ ){
            if(minusOneMonth.isAfter(LocalDate.parse(journey.getDepartureDate()).withDayOfMonth(1)) && journey.getCanceled() == 0){
                bookedJourneys.remove(h);
            }
        }

        //determine wich programm will be used for booking.
        //update bonusprogramm for display and further calculations.
        if(bookedJourneys.size() >= 5) {
            if (initialSize % 49 == 0) {
            }
            if (initialSize % 9 == 0 && initialSize % 49 != 0) {
                loggedIn.setBonus(bonusService.findBonusById(3));
            } else {
                loggedIn.setBonus(bonusService.findBonusById(2));
            }
        }
        else {
                loggedIn.setBonus(bonusService.findBonusById(1));
        }

        userService.updateUser(loggedIn);

        //alter price depending on bonusprogram.
        double modifiedprice = price;
        switch (loggedIn.getBonus().getId()){

            case 3:  modifiedprice = price * 0.9; break;
            case 4:  modifiedprice = 0.00; break;

        }
        //if genug_platz == true place a booking and handle money transfer from logged in user to driver.
        if(genug_platz == true){
            for(Leg leg : legsOnDb){
                leg.getPassengers().add(loggedIn);
                legService.saveLeg(leg);
            }

            //send booking mail to logged in user.
            try {
                GmailService.sendBookingMailPassenger(loggedIn);
            } catch (MailException e) {
                e.printStackTrace();
            }

            //send booking mail to driver.
            try {
                GmailService.sendBookingMailDriver(journey.getDriver());
            } catch (MailException e) {
                e.printStackTrace();
            }

            //save booking object on database.
            booking.setJourney(journey);
            booking.setAmount(modifiedprice);
            booking.setBags(selectedLegs.get(0).getBags());
            bookingService.save(booking);

            //save transaction object ondatabase.
            transaction.setReceiver(journey.getDriver());
            transaction.setAmount(modifiedprice);
            transaction.setTransmitter(loggedIn);
            transaction.setMessage("Bezahlung für die Teilnahme an der Fahrt von "+journey.getOrigin().replaceFirst(", Deutschland","")+" nach "+journey.getDestination().replaceFirst(", Deutschland","")+", Sender: "+loggedIn.getName()+" "+loggedIn.getLastName()+", Empfänger: "+journey.getDriver().getName()+" "+journey.getDriver().getLastName()+" ,Bonusprogramm: "+loggedIn.getBonus().getId());
            transactionService.saveTransaction(transaction);

            //update Account object of logged in user.
            userAccount.setBalance(userAccount.getBalance()-modifiedprice);
            accountService.updateAccount(userAccount);

            //update Account object of driver.
            driverAccount.setBalance(driverAccount.getBalance()+price);
            accountService.updateAccount(driverAccount);
            }


        //add logged in as "user to modelAndView.
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
