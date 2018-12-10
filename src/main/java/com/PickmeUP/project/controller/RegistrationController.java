package com.PickmeUP.project.controller;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    @Autowired

    private UserRepository userRepository;


    @GetMapping("/add")
    public @ResponseBody String addNewUser (@RequestParam String first_name, @RequestParam String last_name){
        User n = new User();
        n.setFirst_name(first_name);
        n.setLast_name(last_name);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String userSubmit(@ModelAttribute User user) {

        return "result";
    }

}
