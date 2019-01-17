package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.JourneyRepository;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyRepository journeyRepository;

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user != null){
            modelAndView.addObject("user", user);
            modelAndView.setViewName("Home_Angemeldet");
        }
        else {
            modelAndView.setViewName("Home_Unangemeldet");
        }

        return modelAndView;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public ModelAndView handleSearch(@RequestParam String von,@RequestParam String nach,@RequestParam String datum){
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Journey> journeys = journeyRepository.findJourneysByPossibleRoute(von,nach);



        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
