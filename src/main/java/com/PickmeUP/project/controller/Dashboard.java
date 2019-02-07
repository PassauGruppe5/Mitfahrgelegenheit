package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.AccountService;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.LegService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class Dashboard {

    @Autowired
    AccountService accountService;

    @Autowired
    LegService legService;

    @Autowired
    JourneyService journeyService;

    @Autowired
    UserService userService;

    //      handles admin dashboard presentation.
    //
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value={"/admin/dashboard"}, method = RequestMethod.GET)
    public ModelAndView showDashboard(){

        //get currently logged in user.
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //store SQL result of total balance.
        double totalBalance = accountService.getTotalBalance();
        Account totalAccount = new Account();
        totalAccount.setBalance(totalBalance);

        //store SQL result of top five destination.
        ArrayList<Object[]> topFiveDestinations = legService.topOfALl();

        //store SQL result of top five drivers.
        ArrayList<Object[]> topFiveDrivers    = journeyService.findTopDrivers();

        //store SQL result of top five passengers.
        ArrayList<Object[]> topFivePassengers    = legService.findTopPassengers();

        //store SQL result of all active journeys.
        ArrayList<Journey> activeJourneys = journeyService.findAllByActiveAndCanceled(1,0);

        //store SQL result of all inactive journey, that are not canceled.
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(0,0);

        //add objects to modelAndView for presentation purposes.
        //set response html page.
        modelAndView.addObject("topDestinations",topFiveDestinations);
        modelAndView.addObject("doneJourneys",doneJourneys);
        modelAndView.addObject("topDrivers",topFiveDrivers);
        modelAndView.addObject("topPassengers",topFivePassengers);
        modelAndView.addObject("activeJourneys",activeJourneys);
        modelAndView.addObject("total",totalAccount);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/admin/Dashboard");
        return modelAndView;
    }
}
