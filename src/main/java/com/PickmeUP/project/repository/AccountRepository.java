package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    //      finds Account by id.
    //
    //      @param id                   id to be looked for.
    //      @return Account             the Account.
    Account findById(int id);

    //      finds Account by user.
    //
    //      @param user                 user to be looked for.
    //      @return Account             the Account.
    Account findByUser(User user);

    //      finds total balance by user.
    //
    //      @return double
    @Query(value = "SELECT SUM(balance) from accounts", nativeQuery = true)
    double getTotalBalance();
}