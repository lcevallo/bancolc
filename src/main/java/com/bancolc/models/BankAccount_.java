package com.bancolc.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BankAccount.class)
public abstract class BankAccount_ {

	public static volatile SingularAttribute<BankAccount, String> accountHolder;
	public static volatile SingularAttribute<BankAccount, Long> bankId;
	public static volatile SingularAttribute<BankAccount, Long> bankAccountId;
	public static volatile SingularAttribute<BankAccount, String> accountNumber;
	public static volatile SingularAttribute<BankAccount, String> ifsc;

}

