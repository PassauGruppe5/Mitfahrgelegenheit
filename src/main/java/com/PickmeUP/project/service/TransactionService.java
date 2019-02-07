package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Transaction;
import com.PickmeUP.project.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    //      saves Transaction.
    //
    //      @param  transaction                     transaction to be saved.
    //      @return void
    public void saveTransaction(Transaction transaction){transactionRepository.save(transaction);}
}
