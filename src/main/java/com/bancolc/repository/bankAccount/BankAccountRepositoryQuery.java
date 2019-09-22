package com.bancolc.repository.bankAccount;

import com.bancolc.models.BankAccount;
import com.bancolc.repository.filter.BankAccountFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankAccountRepositoryQuery {
  public Page<BankAccount> filtrar(BankAccountFilter bankAccountFilter , Pageable pageable);
}
