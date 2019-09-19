package com.bancolc.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="bank", schema = "public")
public class Bank implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "bancoSeg" ,strategy= GenerationType.SEQUENCE)
  @SequenceGenerator(name = "bancoSeg", sequenceName = "bank_bank_id_seq", allocationSize = 1, initialValue = 1)
  @Column(name = "bank_id")
  private Long bankId;

  @Column(name = "bank_name",length = 250, nullable = false)
  private String bankName;

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

}