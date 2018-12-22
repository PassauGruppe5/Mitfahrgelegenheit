package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private Account account = new Account();
}
