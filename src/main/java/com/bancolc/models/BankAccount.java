package com.bancolc.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="bank_account", schema = "public")
public class BankAccount  implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "bank_account_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long bankAccountId;

  @NotNull
  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_holder")
  private String accountHolder;

  @Column(name = "bank_id")
  private Long bankId;

  @Column(name = "ifsc")
  private String ifsc;


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

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getIfsc() {
    return ifsc;
  }

  public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
  }
}
