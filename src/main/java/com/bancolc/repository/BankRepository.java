package com.bancolc.repository;

import com.bancolc.models.Bank;
import com.bancolc.repository.filter.BankFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Long> {

  Page<Bank> filtrar(BankFilter bankFilter, Pageable pageable);
}
