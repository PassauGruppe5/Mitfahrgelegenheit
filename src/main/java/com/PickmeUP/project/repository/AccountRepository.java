package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Account;
import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findById(int id);

    Account findByUser(User user);
}