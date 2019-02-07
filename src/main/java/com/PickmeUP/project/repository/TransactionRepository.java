package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //      finds Transaction by role.
    //
    //      @param id                       id to be looked for.
    //      @return Transaction              the Transaction.
    Transaction findById(int id);
}