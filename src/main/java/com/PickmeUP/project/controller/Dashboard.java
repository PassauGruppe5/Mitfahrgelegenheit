package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.service.AccountService;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.LegService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value={"/admin/dashboard"}, method = RequestMethod.GET)
    public ModelAndView showDashboard(){
        ModelAndView modelAndView = new ModelAndView();
        double totalBalance = accountService.getTotalBalance();
        ArrayList<Object[]> topfiveDestinations= legService.topOfALl();
        ArrayList<Object> topfiveDestinationsClean = new ArrayList<Object>();
        for(Object object: topfiveDestinations){

        }
        ArrayList<Journey> activeJourneys = journeyService.findAllByActiveAndCanceled(1,0);
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(0,1);

        modelAndView.addObject("topDestinations",topfiveDestinations);
        modelAndView.addObject("doneJourneys",doneJourneys);
        modelAndView.addObject("activeJourneys",activeJourneys);
        modelAndView.addObject("total",totalBalance);
        return modelAndView;
    }
}
