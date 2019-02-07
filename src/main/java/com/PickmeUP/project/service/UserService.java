package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.Bonus;
import com.PickmeUP.project.model.Role;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.AccountRepository;
import com.PickmeUP.project.repository.BonusRepository;
import com.PickmeUP.project.repository.RoleRepository;
import com.PickmeUP.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AccountRepository accountRepository;
    private BonusRepository bonusRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       BonusRepository bonusRepository,
                       AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bonusRepository = bonusRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
    }

    //      finds user by email.
    //
    //      @param  email                     email to look for.
    //      @return User                      the user.
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //      saves User.
    //      encrypts password before saving and sets default values for active status, role and bonusprogram.
    //
    //      @param  user                     user to be saved.
    //      @return User
    public void saveUser(User user) {
        Account account = new Account();

        //use bCrypt to encode password.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        //mark user as active.
        user.setActive(1);

        //set bonusprogram to no no bonusprogram.
        Bonus userBonus = bonusRepository.findById(1);
        user.setBonus(userBonus);

        //set inital role as "USER"
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        //save user.
        userRepository.save(user);
        account.setUser(user);
        accountRepository.save(account);

    }

    //      updates User.
    //
    //      @param  user                     user to be updated..
    //      @return void
    public void updateUser(User user) {userRepository.save(user);}

    //      finds User by id.
    //
    //      @param  id                     id to be looked for.
    //      @return User                   the User.
    public User findUserById(int id){return userRepository.findById(id);}
}
