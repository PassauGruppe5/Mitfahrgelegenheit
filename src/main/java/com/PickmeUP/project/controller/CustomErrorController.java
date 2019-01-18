package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private UserService userService;

    @RequestMapping(value = PATH, method = RequestMethod.GET)
    public ModelAndView handleError() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedIn = userService.findUserByEmail(auth.getName());
        if (loggedIn == null) {
            modelAndView.setViewName("/error_unangemeldet");
        }

        else {
            modelAndView.setViewName("/error_angemeldet");
        }

        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
