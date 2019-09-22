package com.bancolc.service;

import com.bancolc.models.Bank;
import com.bancolc.models.BankAccount;
import com.bancolc.repository.BankAccountRepository;
import com.bancolc.repository.BankRepository;
import com.bancolc.service.exception.BancoInexistenteException;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private BankRepository bankRepository;

  public BankAccount salvar(BankAccount bankAccount) {
    Optional<Bank> bank = bankRepository.findById(bankAccount.getBankId());

    if(!bank.isPresent())
    {
      throw new BancoInexistenteException();
    }

    if(bankAccount.getBankAccountId()==0){
      return bankAccountRepository.save(bankAccount);
    }
    else {
      return this.actualizar(bankAccount.getBankAccountId(), bankAccount);
    }

  }


  public BankAccount actualizar(Long bankAccountId, BankAccount bankAccount)
  {
    BankAccount bankAccountSalva = buscarBankAccountExistente(bankAccountId);

    if(!bankAccount.getBankAccountId().equals(bankAccountSalva.getBankAccountId()))
    {
      validarBanco(bankAccount);
    }

    BeanUtils.copyProperties(bankAccount, bankAccountSalva, "bankAccountId");

    return bankAccountRepository.save(bankAccountSalva);

  }




  private void validarBanco(BankAccount bankAccount) {

    if(bankAccount.getBankId() != null){
      Optional<Bank> bankBase = bankRepository.findById(bankAccount.getBankId());
          if(!bankBase.isPresent()){
            throw  new BancoInexistenteException();
          }
    }
    else{
      throw  new BancoInexistenteException();
    }
  }


  private BankAccount buscarBankAccountExistente(Long codigo) {

    Optional<BankAccount> bankAccountEx = bankAccountRepository.findById(codigo);

    if(bankAccountEx == null) {
      throw new IllegalArgumentException();
    }

    return bankAccountEx.get();
  }
}
