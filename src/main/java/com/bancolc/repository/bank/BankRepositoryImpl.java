package com.bancolc.repository.bank;

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

import com.bancolc.models.Bank;
import com.bancolc.models.Bank_;
import com.bancolc.repository.filter.BankFilter;

public class BankRepositoryImpl implements BankRepositoryQuery{


  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Bank> filtrar(BankFilter BankFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Bank> criteria = builder.createQuery(Bank.class);

    Root<Bank> root = criteria.from(Bank.class);

    Predicate[] predicates= crearRestricciones(BankFilter, builder, root);
    criteria.where(predicates);


    TypedQuery<Bank> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);


    return new PageImpl<>(query.getResultList(), pageable, total(BankFilter)) ;
  }


  private Predicate[] crearRestricciones(BankFilter BankFilter, CriteriaBuilder builder,
      Root<Bank> root)
  {

    List<Predicate> predicates = new ArrayList<>();

    if(!StringUtils.isEmpty(BankFilter.getBankName())) {
      predicates.add(builder.like(
          builder.lower(root.get(Bank_.bankName)),
          "%"+BankFilter.getBankName().toLowerCase()+ "%"
      ));
    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void adicionarRestricionDePaginacion(TypedQuery<?> query, Pageable pageable) {

    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;

    query.setFirstResult(primerRegistroDePagina);

    query.setMaxResults(totalRegistroPorPagina);
  }

  private Long total(BankFilter bankFilter) {

    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<Bank> root = criteria.from(Bank.class);

    Predicate[] predicates = crearRestricciones(bankFilter, builder, root);

    criteria.where(predicates);

    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();

  }

}
