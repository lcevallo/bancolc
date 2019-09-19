package com.bancolc.repository.bank;

import com.bancolc.models.Bank;
import com.bancolc.repository.filter.BankFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankRepositoryQuery {
  public Page<Bank> filtrar(BankFilter lanzamientoFilter, Pageable pageable);
}
