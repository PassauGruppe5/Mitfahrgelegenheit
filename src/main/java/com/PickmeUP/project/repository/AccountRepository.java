package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(int id);
}