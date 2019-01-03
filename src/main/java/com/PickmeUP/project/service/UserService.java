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

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        Account account = new Account();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Bonus userBonus = bonusRepository.findById(1);
        user.setBonus(userBonus);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
        account.setUser(user);
        accountRepository.save(account);

    }

}
