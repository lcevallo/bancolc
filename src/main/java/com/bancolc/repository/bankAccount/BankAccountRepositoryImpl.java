package com.bancolc.repository.bankAccount;


import com.bancolc.models.BankAccount;
import com.bancolc.models.BankAccount_;
import com.bancolc.repository.filter.BankAccountFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


public class BankAccountRepositoryImpl implements BankAccountRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;


  @Override
  public Page<BankAccount> filtrar(BankAccountFilter bankAccountFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<BankAccount> criteria = builder.createQuery(BankAccount.class);

    Root<BankAccount> root = criteria.from(BankAccount.class);

    Predicate[] predicates= crearRestricciones(bankAccountFilter, builder, root);
    criteria.where(predicates);


    TypedQuery<BankAccount> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);


    return new PageImpl<>(query.getResultList(), pageable, total(bankAccountFilter)) ;
  }




  private Predicate[] crearRestricciones(BankAccountFilter bankAccountFilter, CriteriaBuilder builder,
      Root<BankAccount> root) {

    List<Predicate> predicates = new ArrayList<>();

    if(!StringUtils.isEmpty(bankAccountFilter.getAccountHolder())) {
      predicates.add(builder.like(
          builder.lower(root.get(BankAccount_.accountHolder)),
          "%"+bankAccountFilter.getAccountHolder().toLowerCase()+ "%"
      ));
    }

    if(!StringUtils.isEmpty(bankAccountFilter.getAccountNumber())) {
      predicates.add(builder.like(
          builder.lower(root.get(BankAccount_.accountNumber)),
          "%"+bankAccountFilter.getAccountNumber().toLowerCase()+ "%"
      ));
    }

    if(!StringUtils.isEmpty(bankAccountFilter.getIfsc())) {
      predicates.add(builder.like(
          builder.lower(root.get(BankAccount_.ifsc)),
          "%"+bankAccountFilter.getIfsc().toLowerCase()+ "%"
      ));
    }

    return predicates.toArray(new Predicate[predicates.size()]);

  }


  private void adicionarRestricionDePaginacion(TypedQuery<BankAccount> query, Pageable pageable) {
    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;

    query.setFirstResult(primerRegistroDePagina);

    query.setMaxResults(totalRegistroPorPagina);
  }

  private Long total(BankAccountFilter bankAccountFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<BankAccount> root = criteria.from(BankAccount.class);

    Predicate[] predicates = crearRestricciones(bankAccountFilter, builder, root);

    criteria.where(predicates);

    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();

  }


}
