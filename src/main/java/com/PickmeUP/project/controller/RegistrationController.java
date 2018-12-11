package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired

    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView( "registration", "user", new User());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String userSubmit(@Valid @ModelAttribute ("user") User user , BindingResult result, ModelMap model) {
        if(result.hasFieldErrors()) {
            return "error";
        }
        userService.addUser(user);
        model.addAttribute("first_name", user.getFirst_name());
        model.addAttribute ("last_name", user.getLast_name());
        return "resultRegistration";
    }

}
