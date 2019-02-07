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


    //      Handles display of Spring Boot Security Registration page.
    //
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        //generate new User object.
        User user = new User();

        // add user to model and View.
        modelAndView.addObject("user", user);

        //set result page to "registration".
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    //      Handles Spring Boot Security Registration.
    //
    //      @param user                 the user to be registered.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws MailException, ParseException {
        ModelAndView modelAndView = new ModelAndView();

        //check if email is not yet used.
        User userExists = userService.findUserByEmail(user.getEmail());

        //only allow people of age 16 or older to register.
        LocalDate current = LocalDate.now().minusYears(16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //if email is already user, reject value and add error message.
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user", "Es existiert bereits ein User mit der angegebenen Email.");
        }

        //if name not only contains letters, reject value and add error message.
        if(user.getName().matches("[a-zA-Z]+") != true){
            bindingResult
                    .rejectValue("name", "error.user", "Der Name darf nur aus Buchstaben bestehen.");
        }

        //if lastName not only contains letters, reject value and add error message.
        if(user.getLastName().matches("[a-zA-Z]+") != true){
            bindingResult
                    .rejectValue("lastName", "error.user", "Der Nachname darf nur aus Buchstaben bestehen.");
        }

        //if user is not at least 16 years old, reject value and add error message.
        if(current.isBefore(LocalDate.parse(user.getBirth(), formatter)) == true){
            bindingResult
                    .rejectValue("birth", "error.user", "Das Geburtsdatum ist ungültig.");
        }

        //if enterd phoen number is longer than 15 digits or shorter than 13 or contains letters, reject value and add error message.
        if (user.getPhone().matches("[0-9]+") && user.getPhone().length() <= 15 && user.getPhone().length() >= 13) {
            bindingResult
                    .rejectValue("phone", "error.user", "Die angegebene Telefonnummer ist ungültig.");
        }

        //if an exception rises set result page to "registration".
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }

        //if no exceptions rises, save registered user.
        else
        {

            //save  user.
            userService.saveUser(user);

            //send welcome mail.
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
