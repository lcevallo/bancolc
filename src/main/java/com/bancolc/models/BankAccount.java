package com.bancolc.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="bank_account", schema = "public")
public class BankAccount {

  @Id
  @Column(name = "bank_account_id", nullable = false)
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long bankAccountId;

  @NotNull
  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_holder")
  private String accountHolder;

  @Column(name = "bank_id")
  private UUID bankId;

  @Column(name = "ifsc")
  private String IFSC;


  public Long getBankAccountId() {
    return bankAccountId;
  }

  public void setBankAccountId(Long bankAccountId) {
    this.bankAccountId = bankAccountId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountHolder() {
    return accountHolder;
  }

  public void setAccountHolder(String accountHolder) {
    this.accountHolder = accountHolder;
  }

  public UUID getBankId() {
    return bankId;
  }

  public void setBankId(UUID bankId) {
    this.bankId = bankId;
  }

  public String getIFSC() {
    return IFSC;
  }

  public void setIFSC(String IFSC) {
    this.IFSC = IFSC;
  }

}
