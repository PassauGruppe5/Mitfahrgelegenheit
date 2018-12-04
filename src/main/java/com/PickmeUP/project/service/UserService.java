package com.PickmeUP.project.service;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        userRepository.save(user);
        return user;
    }

}
