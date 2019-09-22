package com.bancolc.resource;

import com.bancolc.event.RecursoCreadoEvent;
import com.bancolc.exceptionhandler.BancoLcExceptionHandler.Error;
import com.bancolc.models.BankAccount;
import com.bancolc.repository.BankAccountRepository;
import com.bancolc.repository.filter.BankAccountFilter;
import com.bancolc.service.BankAccountService;
import com.bancolc.service.exception.BancoInexistenteException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank_account")
public class BankAccountResource {

  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private BankAccountService bankAccountService;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;

  @GetMapping
  public Page<BankAccount> buscar(BankAccountFilter bankAccountFilter, Pageable pageable) {
    return bankAccountRepository.filtrar(bankAccountFilter, pageable);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<BankAccount>  buscarPorCodigo(@PathVariable Long codigo) {

    Optional<BankAccount> bankAccountBase = bankAccountRepository.findById(codigo);
    return bankAccountBase.isPresent() ? ResponseEntity.ok(bankAccountBase.get()) : ResponseEntity.notFound().build();

  }


  @PostMapping
  public ResponseEntity<BankAccount> crear(@Valid @RequestBody BankAccount bankAccount, HttpServletResponse response)
  {
    BankAccount bankAccountSalvo = bankAccountService.salvar(bankAccount);

    publisher.publishEvent(new RecursoCreadoEvent(this, response, bankAccountSalvo.getBankAccountId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountSalvo);
  }

  @ExceptionHandler({BancoInexistenteException.class})
  public ResponseEntity<Object> handlePersonaInactivaOInexistenteException(BancoInexistenteException ex)
  {
    String mensajeUsuario=messageSource.getMessage("banco.inexisten-o-inactiva",null, LocaleContextHolder
        .getLocale());
    String mensajeDesarrollador=ex.toString();
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario, mensajeDesarrollador));
    return ResponseEntity.badRequest().body(errores);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<BankAccount> actualizar(@PathVariable Long codigo, @Valid @RequestBody BankAccount bankAccount){

    try {
      BankAccount bankAccountSalva  = bankAccountService.actualizar(codigo, bankAccount);
      return ResponseEntity.ok(bankAccountSalva);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo)
  {
    bankAccountRepository.deleteById(codigo);

  }

}
