package com.bancolc.resource;

import com.bancolc.event.RecursoCreadoEvent;
import com.bancolc.models.Bank;
import com.bancolc.repository.BankRepository;
import com.bancolc.repository.filter.BankFilter;
import com.bancolc.service.BankService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banco")
public class BancoResource {

   @Autowired
   private BankRepository bankRepository;

   @Autowired
   private BankService bankService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;


  public Page<Bank> buscar(BankFilter bankFilter, Pageable pageable){
    return bankRepository.filtrar(bankFilter, pageable);

  }

  @GetMapping
  public List<Bank> encontrar(){
      return bankRepository.findAll();
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Bank>  buscarPorCodigo(@PathVariable Long bankAccountId) {
    Optional<Bank> bancoBase = bankRepository.findById(bankAccountId);

    return bancoBase.isPresent() ? ResponseEntity.ok(bancoBase.get()) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Bank> crear(@Valid @RequestBody Bank banco, HttpServletResponse response)
  {
    Bank bancoSalvo = bankService.salvar(banco);

    publisher.publishEvent(new RecursoCreadoEvent(this, response, bancoSalvo.getBankId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(bancoSalvo);
  }


}

