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
        Account account = accountService.findbyUser(loggedIn);

            for(Leg leg : legs){
                leg.correctAddresses(leg.getStart_address(), leg.getEnd_address() );
            }
        modelAndView.addObject("account", account);
        modelAndView.addObject("legs",legs);
        modelAndView.addObject("driver",driver);
        modelAndView.addObject("journey",journey);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/booking/create/Buchung_anlegen");
        return modelAndView;
    }

    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    public ModelAndView createBooking(@RequestBody ArrayList<Leg> selectedLegs) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journey = legService.findById(selectedLegs.get(0).getId()).getJourney();
        Account userAccount = accountService.findbyUser(loggedIn);
        Account driverAccount = accountService.findbyUser(journey.getDriver());
        Transaction transaction = new Transaction();
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate minusOneMonth = now.minusMonths(1);
        List<Journey> bookedJourneys = journeyService.findJourneysByLegs(legService.findLegsByPassengersContaining(loggedIn));
        System.out.println(ZonedDateTime.now());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        double price = 0.00;
        Boolean genug_platz = true;

        ArrayList<Leg> legsOnDb = new ArrayList<>();

        for(Leg leg : selectedLegs){
            legsOnDb.add(legService.findById(leg.getId()));
            price = Precision.round(price + journey.getPriceKm() * leg.getDistance()/1000,2);
            if(legService.findById(leg.getId()).checkSpace() == false){
                genug_platz = false;

            }
        }
        price = price + selectedLegs.get(0).getBags() *  journey.getPriceBag();

        int initialSize = bookedJourneys.size();
        for(int h = 0; h < bookedJourneys.size(); h++ ){
            if(minusOneMonth.isAfter(LocalDate.parse(journey.getDepartureDate()).withDayOfMonth(1)) && journey.getCanceled() == 0){
                bookedJourneys.remove(h);
            }
        }

        if(bookedJourneys.size() >= 5) {
            if (initialSize % 49 == 0) {
                loggedIn.setBonus(bonusService.findBonusById(4));
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

        double modifiedprice = price;
        switch (loggedIn.getBonus().getId()){

            case 3:  modifiedprice = price * 0.9; break;
            case 4:  modifiedprice = 0.00; break;

        }

        if(genug_platz == true){
            for(Leg leg : legsOnDb){
                leg.getPassengers().add(loggedIn);
                legService.saveLeg(leg);
            }

            try {
                GmailService.sendBookingMail(loggedIn);
            } catch (MailException e) {
                e.printStackTrace();
            }

            transaction.setReceiver(journey.getDriver());
            transaction.setAmount(modifiedprice);
            transaction.setTransmitter(loggedIn);
            transaction.setMessage("Bezahlung für die Teilnahme an der Fahrt von "+journey.getOrigin().replaceFirst(", Deutschland","")+" nach "+journey.getDestination().replaceFirst(", Deutschland","")+", Sender: "+loggedIn.getName()+" "+loggedIn.getLastName()+", Empfänger: "+journey.getDriver().getName()+" "+journey.getDriver().getLastName()+" ,Bonusprogramm: "+loggedIn.getBonus().getId());
            transactionService.saveTransaction(transaction);

            userAccount.setBalance(userAccount.getBalance()-modifiedprice);
            accountService.updateAccount(userAccount);

            driverAccount.setBalance(driverAccount.getBalance()+price);
            accountService.updateAccount(driverAccount);
            }



        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
