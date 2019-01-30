package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.GmailService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws MailException, ParseException {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        LocalDate current = LocalDate.now().minusYears(16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user", "Es existiert bereits ein User mit der angegebenen Email.");
        }
        if(user.getName().matches("[a-zA-Z]+") != true){
            bindingResult
                    .rejectValue("name", "error.user", "Der Name darf nur aus Buchstaben bestehen.");
        }
        if(user.getLastName().matches("[a-zA-Z]+") != true){
            bindingResult
                    .rejectValue("lastName", "error.user", "Der Nachname darf nur aus Buchstaben bestehen.");
        }
        if(current.isBefore(LocalDate.parse(user.getBirth(), formatter)) == true){
            bindingResult
                    .rejectValue("birth", "error.user", "Das Geburtsdatum ist ungültig.");
        }
        if (user.getPhone().matches("[0-9]+") && user.getPhone().length() <= 15 && user.getPhone().length() >= 13) {
            bindingResult
                    .rejectValue("phone", "error.user", "Die angegebene Telefonnummer ist ungültig.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }

        else
        {

        userService.saveUser(user);
        try {
            GmailService.sendWelcomeMail(user);
        } catch (MailException e) {
            e.printStackTrace();
        }
            modelAndView.addObject("successMessage", "User wurde erfolgreich registriert");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }
}
