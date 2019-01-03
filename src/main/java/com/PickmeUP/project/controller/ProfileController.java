package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.AccountService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value={"/profile/overwiev"}, method = RequestMethod.GET)
    public ModelAndView showProfileOverwiew() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user == null){
        modelAndView.setViewName("login");
        }
        else{
            modelAndView.addObject("user", user);
            modelAndView.setViewName("profile/overwiev");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/profile/balance"}, method = RequestMethod.GET)
    public ModelAndView showBalance() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user == null){
            modelAndView.setViewName("login");
        }
        else{
            Account account = accountService.findbyUser(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("account", account);
            modelAndView.setViewName("profile/balance");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/profile/payment-in"}, method = RequestMethod.GET)
    public ModelAndView ShowPaymentInForm(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user == null){
            modelAndView.setViewName("login");
        }
        else{
            Account account = accountService.findbyUser(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("account", account);
            modelAndView.setViewName("profile/payment-in");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/profile/payment-out"}, method = RequestMethod.GET)
    public ModelAndView ShowPaymentOutForm(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user == null){
            modelAndView.setViewName("login");
        }
        else{
            Account account = accountService.findbyUser(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("account", account);
            modelAndView.setViewName("profile/payment-out");
        }
        return modelAndView;
    }
}
