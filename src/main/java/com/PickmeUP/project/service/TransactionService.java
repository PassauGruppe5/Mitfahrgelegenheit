package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Transaction;
import com.PickmeUP.project.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    private Transaction transaction = new Transaction();
}
