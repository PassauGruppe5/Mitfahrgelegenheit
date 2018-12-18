package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.UserService;
import com.PickmeUP.project.validation.EmailExistsError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.websocket.OnError;

@Controller
public class RegistrationController {

    @Autowired

    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView userSubmit(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) throws EmailExistsError {

        User userToRegister = new User();

        if (!result.hasErrors()) {
            userToRegister = userService.addUser(user);
        }
        if (userToRegister == null){
            throw new EmailExistsError(
                    "Die Email: "+user.getEmail()+" wird bereits von einem anderen User genutzt!");
        }

        if (result.hasErrors()) {
            return new ModelAndView("error", "user", user);
        }
        else {

            return new ModelAndView("resultRegistration", "user", user);
        }

    }

    @ExceptionHandler(value = EmailExistsError.class)
    public String handleException() {
        return "registrationEmailError";
    }
}

