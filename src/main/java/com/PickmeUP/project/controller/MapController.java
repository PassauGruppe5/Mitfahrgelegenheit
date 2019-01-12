package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.UserService;
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

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ModelAndView ShowMap(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/map");
        return modelAndView;
    }

    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public ModelAndView handleJourney(@RequestBody Journey journey){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        Journey journeyToSave = journey;
        journeyToSave.setDriver(loggedIn);
        journeyService.saveJourney(journeyToSave);
        modelAndView.addObject("user", loggedIn);
        modelAndView.setViewName("Home_Angemeldet");
        return modelAndView;
    }
}
