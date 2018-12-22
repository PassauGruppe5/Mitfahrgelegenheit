package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(int id);
}