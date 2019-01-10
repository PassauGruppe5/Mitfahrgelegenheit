package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Journey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController {

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ModelAndView ShowMap(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/map");
        return modelAndView;
    }

    @RequestMapping(value = "/Journey/create", method = RequestMethod.POST)
    public @ResponseBody String handleJourney(@RequestBody Journey journey){

        System.out.println(journey);

        return "Dies ist eine erfolgreiche Ausgabe";
    }
}
