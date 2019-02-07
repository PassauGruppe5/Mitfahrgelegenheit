package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.*;
import com.PickmeUP.project.repository.AccountRepository;
import com.PickmeUP.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private JourneyService journeyService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LegService legService;


    //      Handles payment-in form details.
    //
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value={"/profile/create/payment-in"}, method = RequestMethod.GET)
    public ModelAndView ShowPaymentInForm(){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        //if user is not logged in set result page to "login".
        //else show payment-in form.
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

    //      Handles payment into user account.
    //
    //      @param account              account object containing information for payment-in.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/profile/create/payment-in", method = RequestMethod.POST)
    public ModelAndView addBalance(@Valid Account account ){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        //get Account of logged in user.
        Account accountToUpdate = accountRepository.getOne(accountService.findbyUser(user).getId());

        //generate new Transaction object.
        Transaction transaction = new Transaction();

        //if balance to be payed in is less then 0, add errorMessage to modelAndView.
        if (account.getBalance()<= 0) {
            modelAndView.addObject("errorMessage", "Der Betrag muss mindestens 0.01 betragen.");
            modelAndView.setViewName("/profile/create/payment-in");
            modelAndView.addObject("user", user);
            modelAndView.addObject("account",account);
            return modelAndView;
        }

        //if balance to be payed in has more than 2 dcimal digits, add errorMessage to modelAndView.
        if(BigDecimal.valueOf(account.getBalance()).scale() > 2){

            modelAndView.addObject("errorMessage", "Ungültiger Betrag.");
            modelAndView.setViewName("/profile/create/payment-in");
            modelAndView.addObject("user", user);
            modelAndView.addObject("account",account);
            return modelAndView;
        }

        //add balance to user's account.
         else{
            accountToUpdate.setBalance(accountToUpdate.getBalance()+account.getBalance());
            accountService.updateAccount(accountToUpdate);

            //fill and save transaction
            transaction.setMessage("Einzahlung an eigenes Konto.");
            transaction.setAmount(account.getBalance());
            transaction.setReceiverAndTransmitter(user);
            transactionService.saveTransaction(transaction);

            //add success message to modelAndView.
            modelAndView.addObject("successMessage","Der Betrag: "+account.getBalance()+"€ wurde erfolgreich eingebucht.");
        }
        modelAndView.setViewName("/profile/create/payment-in");
        modelAndView.addObject("user", user);
        modelAndView.addObject("account",account);
        return modelAndView;
    }

    //      Handles display of payment-out form.
    //
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value={"/profile/create/payment-out"}, method = RequestMethod.GET)
    public ModelAndView ShowPaymentOutForm(){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        //if user is not logged in, set result page to "login".
        //else present payment-out form.
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

    //      Handles payment out of user account.
    //
    //      @param account              account object containing information for payment-out.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value = "/profile/create/payment-out", method = RequestMethod.POST)
    public ModelAndView substractBalance(@Valid Account account ){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        //get Account object of logged in user.
        Account accountToUpdate = accountRepository.getOne(accountService.findbyUser(user).getId());

        //generate new Transaction object.
        Transaction transaction = new Transaction();

        //if balance to be payed out is less balance on user's account, add error message.
        if (accountToUpdate.getBalance()-account.getBalance()< 0) {
            modelAndView.addObject("user",user);
            modelAndView.addObject("account",account);
            modelAndView.addObject("errorMessage", "Nicht genug Guthaben vorhanden.");
            modelAndView.setViewName("/profile/create/payment-out");
            return modelAndView;
        }

        //if balance to be payed out has more than 2 decimal digits or is less than 0, add error message.
        if(BigDecimal.valueOf(account.getBalance()).scale() > 2 ||account.getBalance() < 0 ){
            modelAndView.setViewName("/profile/create/payment-out");
            modelAndView.addObject("user", user);
            modelAndView.addObject("account",account);
            modelAndView.addObject("errorMessage", "Ungültiger Betrag.");
            return modelAndView;
        }

        //add balance to user's account.
        else{
            accountToUpdate.setBalance(accountToUpdate.getBalance()-account.getBalance());
            accountService.updateAccount(accountToUpdate);

            //fill and save transaction.
            transaction.setMessage("Auszahlung von eigenem Konto.");
            transaction.setAmount(account.getBalance());
            transaction.setReceiverAndTransmitter(user);
            transactionService.saveTransaction(transaction);

            modelAndView.addObject("user",user);
            modelAndView.addObject("account",account);

            //add success message to modelAndView.
            modelAndView.addObject("successMessage","Der Betrag: "+account.getBalance()+"€ wurde erfolgreich abgebucht.");
            modelAndView.setViewName("/profile/create/payment-out");
        }
        return modelAndView;
    }


    //      Handles payment post of rating on user profiles.
    //
    //      @param rating               rating object to be saved.
    //      @param usr                  user id of displayed user profile..
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value="profile/create/rating", method = RequestMethod.POST)
    public ModelAndView handleRatingForm(@Valid Rating rating,@RequestParam String usr){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToView = userService.findUserByEmail(auth.getName());

        //get user that is shown.
        int usrAsInt = Integer.parseInt(usr);
        User userToShow = userService.findUserById(usrAsInt);

        //set receiver and publisher of rating.
        rating.setReceiver(userToShow);
        rating.setPublisher(userToView);

        //set grade of rating to 0.0 if no grade has been chosen and save rating.
        if(rating.getGrade() == null){
            rating.setGrade("0.0");
        }
        ratingService.saveRating(rating);

        modelAndView.addObject("userToShow", userToShow);
        modelAndView.addObject("userToView", userToView);
        modelAndView.addObject("rating", rating);
        modelAndView.setViewName("redirect:/profile/show/profile?id="+userToShow.getId());

        return modelAndView;
    }

    //      Handles update of user comment on own profile page..
    //
    //      @param rating               User object containing additional information.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value="/profile/alter/comment", method = RequestMethod.POST)
    public ModelAndView hadleCommentInput(User user){
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToUpdate = userService.findUserByEmail(auth.getName());

        //uupdate user comment.
        userToUpdate.setComment(user.getComment());
        userService.updateUser(userToUpdate);

        modelAndView.addObject("id",userToUpdate.getId());
        modelAndView.addObject("userToUpdate",userToUpdate);
        modelAndView.setViewName("redirect:/profile/show/profile");

        return modelAndView;

    }

    //      Handles presentation of user profiles.
    //
    //      @param id                   id of user to presentate its profile.
    //      @return modelAndView        the ModelAndView.
    @RequestMapping(value="profile/show/profile", method = RequestMethod.GET)
    public ModelAndView showOwnUserProfile(@RequestParam("id") int id) {
        ModelAndView modelAndView = new ModelAndView();

        //get currently logged in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedIn = userService.findUserByEmail(auth.getName());

        //get user who's profile is to be displayed.
        User toView = userService.findUserById(id);

        //generate new Rating object.
        Rating rating = new Rating();

        //get Account object of logged in user.
        Account account = accountService.findbyUser(loggedIn);

        //get all booked Journeys of logged in user.
        List<Journey> bookedList = journeyService.findJourneysByLegs(legService.findLegsByPassengersContaining(loggedIn));

        //get all journeys of done bookings.
        ArrayList<Journey> doneList = new ArrayList<>();

        //if logged in user equals user of who's profile is to be shown, set html page to "profile_user".
        if(loggedIn == toView){

            //remove canceled bookeings and store active ones in bookedList.
            for (int i = 0; i < bookedList.size(); i++) {
                if(bookedList.get(i).getCanceled() == 0 && bookedList.get(i).getActive() == 0){
                    doneList.add(bookedList.get(i));
                }
                if(bookedList.get(i).getActive() == 0){
                    bookedList.remove(i);
                }
            }

            //get all Ratings of logged in user.
            List<Rating> ratingList = ratingService.getRatingsOfUser(loggedIn.getId());

            //get all Journeys active of logged in user and store active ones in journeyList.
            List<Journey> journeyList = journeyService.findByDriverAndActive(loggedIn, 1);

            //facilitate display of origin and destination of the journeys.
            for(Journey journey : journeyList) {
                journey.setOrigin(journey.getOrigin().replaceFirst(", Deutschland", ""));
                journey.setDestination(journey.getDestination().replaceFirst(", Deutschland", ""));
            }

            for(Journey journey : bookedList) {
                journey.setOrigin(journey.getOrigin().replaceFirst(", Deutschland", ""));
                journey.setDestination(journey.getDestination().replaceFirst(", Deutschland", ""));
            }

            //add objects to modelAndView.
            modelAndView.addObject("user",loggedIn);
            modelAndView.addObject("account",account);
            modelAndView.addObject("ratingList",ratingList);
            modelAndView.addObject("journeyList",journeyList);
            modelAndView.addObject("bookedList",bookedList);
            modelAndView.addObject("doneList",doneList);
            modelAndView.setViewName("/profile/show/profile_user");
        }
        //if logged in user does not equal user of profile.
        else{

            //get all ratings of visited user and store them in ratingList.
            List<Rating> ratingList = ratingService.getRatingsOfUser(toView.getId());

            //add objects to modelAndView.
            modelAndView.addObject("userV", toView);
            modelAndView.addObject("user", loggedIn);
            modelAndView.addObject("rating",rating);
            modelAndView.addObject("ratingList",ratingList);

            //set result page to "userProfile_visitor"
            modelAndView.setViewName("/profile/show/userProfile_visitor");
        }
        return modelAndView;
    }

}
