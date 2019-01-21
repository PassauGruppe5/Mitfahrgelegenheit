package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.*;
import com.PickmeUP.project.service.*;
import org.apache.commons.math3.util.Precision;
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

            for(Leg leg : legs){
                String[] partsOfStartAdress =leg.getStart_address().split(",");
                String[] partsOfEndAdress =leg.getEnd_address().split(",");

                switch (partsOfStartAdress.length ) {
                    case 3:
                        leg.setStart_address(partsOfStartAdress[1]);
                        break;
                    case 4:
                        leg.setStart_address(partsOfStartAdress[2]);
                        break;
                    case 5:
                        leg.setStart_address(partsOfStartAdress[3]);
                        break;
                }

                switch (partsOfEndAdress.length ) {
                    case 3:
                        leg.setEnd_address(partsOfEndAdress[1]);
                        break;
                    case 4:
                        leg.setEnd_address(partsOfEndAdress[2]);
                        break;
                    case 5:
                        leg.setEnd_address(partsOfEndAdress[3]);
                        break;
                }
            }

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
        Journey journey = legService.findById(selectedLegs.get(0).getId()).getJourney();
        Account userAccount = accountService.findbyUser(loggedIn);
        Account driverAccount = accountService.findbyUser(journey.getDriver());
        Transaction transaction = new Transaction();

        double price = 0.00;
        Boolean genug_platz = true;

        ArrayList<Leg> legsOnDb = new ArrayList<>();

        for(Leg leg : selectedLegs){
            legsOnDb.add(legService.findById(leg.getId()));
            price = Precision.round(price + journey.getPriceKm() * leg.getDistance()/1000,2);
            List<User> testingtest = leg.getPassengers();
            if(legService.findById(leg.getId()).checkSpace() == false){
                genug_platz = false;

            }
        }
        price = price + selectedLegs.get(0).getBags() *  journey.getPriceBag();
        if(price > userAccount.getBalance()){
            modelAndView.addObject("errorMessage","Sie haben leider nicht genug Guthaben auf ihrem Konto.");
            modelAndView.addObject("user",loggedIn);
            modelAndView.setViewName("/Home_Angemeldet");
            return modelAndView;
        }

        if(genug_platz == true){
            for(Leg leg : legsOnDb){
                leg.getPassengers().add(loggedIn);
                legService.saveLeg(leg);
            }

            transaction.setReceiver(journey.getDriver());
            transaction.setAmount(price);
            transaction.setTransmitter(loggedIn);
            transaction.setMessage("Bezahlung für die Teilnahme an der Fahrt von "+journey.getOrigin().replaceFirst(", Deutschland","")+" nach "+journey.getDestination().replaceFirst(", Deutschland","")+", Sender: "+loggedIn.getName()+" "+loggedIn.getLastName()+", Empfänger: "+journey.getDriver().getName()+" "+journey.getDriver().getLastName());
            transactionService.saveTransaction(transaction);

            userAccount.setBalance(userAccount.getBalance()-price);
            accountService.updateAccount(userAccount);

            driverAccount.setBalance(driverAccount.getBalance()+price);
            accountService.updateAccount(driverAccount);

            modelAndView.addObject("succesMessage","Erfolgreich zur Fahrt hinzugebucht.");
        }
        else{
            modelAndView.addObject("errorMessage","Da war wohl jemand schneller. Alle Plätze sind bereits vergeben.");
        }



        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
