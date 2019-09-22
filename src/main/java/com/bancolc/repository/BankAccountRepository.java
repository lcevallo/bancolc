package com.bancolc.repository;

import com.bancolc.models.BankAccount;
import com.bancolc.repository.bankAccount.BankAccountRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long>,
    BankAccountRepositoryQuery {


}
