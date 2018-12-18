package com.PickmeUP.project.service;

import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.UserRepository;
import com.PickmeUP.project.validation.EmailExistsError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) throws EmailExistsError {

        if (userRepository.existsByEmail(user.getEmail())){
            return null;
        }


        User userToSave = new User();
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setEmail(user.getEmail());
        userToSave.setBirth(user.getBirth());
        userToSave.setFirst_name(user.getFirst_name());
        userToSave.setLast_name(user.getLast_name());
        userToSave.setPhone(user.getPhone());

        return userRepository.save(userToSave);
    }
}
