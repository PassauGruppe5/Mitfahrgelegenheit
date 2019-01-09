package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.Rating;
import com.PickmeUP.project.model.Transaction;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.AccountRepository;
import com.PickmeUP.project.service.AccountService;
import com.PickmeUP.project.service.RatingService;
import com.PickmeUP.project.service.TransactionService;
import com.PickmeUP.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RatingService ratingService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value={"/profile/create/payment-in"}, method = RequestMethod.GET)
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
            modelAndView.setViewName("profile/create/payment-in");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/profile/create/payment-in", method = RequestMethod.POST)
    public ModelAndView addBalance(@Valid Account account ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Account accountToUpdate = accountRepository.getOne(accountService.findbyUser(user).getId());
        ModelAndView modelAndView = new ModelAndView();
        Transaction transaction = new Transaction();
        if (account.getBalance()<= 0) {
            modelAndView.setViewName("/profile/create/payment-in");
            modelAndView.addObject("errorMessage", "Der Betrag muss mindestens 0.01 betragen.");
        }
        else{
            accountToUpdate.setBalance(accountToUpdate.getBalance()+account.getBalance());
            accountService.updateAccount(accountToUpdate);
            transaction.setMessage("Einzahlung an eigenes Konto.");
            transaction.setAmount(account.getBalance());
            transaction.setReceiverAndTransmitter(user);
            transactionService.saveTransaction(transaction);
            modelAndView.addObject("user",user);
            modelAndView.addObject("account",accountToUpdate);
            modelAndView.addObject("successMessage","Der Betrag: "+account.getBalance()+"€ wurde erfolgreich eingebucht.");
            modelAndView.setViewName("/profile/create/payment-in");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/profile/create/payment-out"}, method = RequestMethod.GET)
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
            modelAndView.setViewName("profile/create/payment-out");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/profile/create/payment-out", method = RequestMethod.POST)
    public ModelAndView substractBalance(@Valid Account account ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Account accountToUpdate = accountRepository.getOne(accountService.findbyUser(user).getId());
        ModelAndView modelAndView = new ModelAndView();
        Transaction transaction = new Transaction();
        if (accountToUpdate.getBalance()-account.getBalance()< 0) {
            modelAndView.setViewName("/profile/create/payment-out");
            modelAndView.addObject("errorMessage", "Nicht genug Guthaben vorhanden.");
        }
        else{
            accountToUpdate.setBalance(accountToUpdate.getBalance()-account.getBalance());
            accountService.updateAccount(accountToUpdate);
            transaction.setMessage("Auszahlung von eigenem Konto.");
            transaction.setAmount(account.getBalance());
            transaction.setReceiverAndTransmitter(user);
            transactionService.saveTransaction(transaction);
            modelAndView.addObject("user",user);
            modelAndView.addObject("account",accountToUpdate);
            modelAndView.addObject("successMessage","Der Betrag: "+account.getBalance()+"€ wurde erfolgreich abgebucht.");
            modelAndView.setViewName("/profile/create/payment-out");
        }
        return modelAndView;
    }

    @RequestMapping(value="profile/create/rating", method = RequestMethod.GET)
    public ModelAndView showRatingForm(@RequestParam("id") int id){
    ModelAndView modelAndView = new ModelAndView();
    User userToShow = userService.findUserById(id);
    Rating rating = new Rating();
    modelAndView.addObject("userToShow", userToShow);
    modelAndView.addObject("rating", rating);
    modelAndView.setViewName("profile/create/rating");

    return modelAndView;

    }

    @RequestMapping(value="profile/create/rating", method = RequestMethod.POST)
    public ModelAndView handleRatingForm(@Valid Rating rating,@RequestParam String usr){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToView = userService.findUserByEmail(auth.getName());
        int usrAsInt = Integer.parseInt(usr);
        User userToShow = userService.findUserById(usrAsInt);
        rating.setReceiver(userToShow);
        rating.setPublisher(userToView);
        ratingService.saveRating(rating);
        modelAndView.addObject("userToShow", userToShow);
        modelAndView.addObject("userToView", userToView);
        modelAndView.addObject("rating", rating);
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @RequestMapping(value="profile/show/rating",method = RequestMethod.GET)
    public ModelAndView showRatings(@RequestParam("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        User userToShow = userService.findUserById(id);
        List<Rating> ratingList = ratingService.getRatingsOfUser(id);
        modelAndView.addObject("user",userToShow);
        modelAndView.addObject("ratingList",ratingList);
        modelAndView.setViewName("/profile/show/rating");
        return modelAndView;
    }

    @RequestMapping(value="profile/show/profile", method = RequestMethod.GET)
    public ModelAndView showOwnUserProfile(@RequestParam("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());
        User toView = userService.findUserById(id);
        List<Rating> ratingList = ratingService.getRatingsOfUser(id);
        Account account = accountService.findbyUser(loggedIn);
        if(loggedIn == toView){
            modelAndView.addObject("user",loggedIn);
            modelAndView.addObject("account",account);
            modelAndView.addObject("ratingList",ratingList);
            modelAndView.setViewName("/profile/show/profile_user");
        }
        else{
            modelAndView.addObject("user", toView);
            modelAndView.setViewName("/profile/show/userProfile_visitor");
        }
        return modelAndView;
    }

}
