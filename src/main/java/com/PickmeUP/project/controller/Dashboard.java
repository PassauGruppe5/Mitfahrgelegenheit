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


    @RequestMapping(value={"/admin/dashboard"}, method = RequestMethod.GET)
    public ModelAndView showDashboard(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        double totalBalance = accountService.getTotalBalance();
        ArrayList<Object[]> topFiveDestinations = legService.topOfALl();
        ArrayList<Object[]> topFiveDrivers    = journeyService.findTopDrivers();
        Account totalAccount = new Account();
        totalAccount.setBalance(totalBalance);

        ArrayList<Journey> activeJourneys = journeyService.findAllByActiveAndCanceled(1,0);
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(0,0);

        modelAndView.addObject("topDestinations",topFiveDestinations);
        modelAndView.addObject("doneJourneys",doneJourneys);
        modelAndView.addObject("topDrivers",topFiveDrivers);
        modelAndView.addObject("activeJourneys",activeJourneys);
        modelAndView.addObject("total",totalAccount);
        modelAndView.addObject("user",loggedIn);
        modelAndView.setViewName("/admin/Dashboard");
        return modelAndView;
    }
}
