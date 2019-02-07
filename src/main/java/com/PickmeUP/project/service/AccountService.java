package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //      saves Account Object by email.
    //      initializes balance of 0.00 at creation.
    //
    //      @param account                    account to be saved.
    //      @return void                       the User.
    public void saveAccount(Account account){
        account.setBalance(0.00);
        accountRepository.save(account);
    }

    //      finds Account by user.
    //
    //      @param user                 user to be looked for.
    //      @return Account             the Account.
    public Account findbyUser(User user) {return accountRepository.findByUser(user);}

    public void updateAccount(Account account){
        accountRepository.save(account);
    }

    public double getTotalBalance(){return accountRepository.getTotalBalance();}

}
