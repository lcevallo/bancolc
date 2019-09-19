package com.bancolc.service;

import com.bancolc.models.Bank;
import com.bancolc.repository.BankRepository;
import com.bancolc.service.exception.BancoInexistenteException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  @Autowired
  private BankRepository bankRepository;

  public Bank salvar(@Valid Bank bank){
    if(bank.getBankId()==null){
      return bankRepository.save(bank);
    }
    else{
      Optional<Bank> bancobase = bankRepository.findById(bank.getBankId());
      if(!bancobase.isPresent()){
        throw new BancoInexistenteException();
      }
      return bankRepository.save(bank);
    }
  }



}
