package com.bancolc.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BancoLcExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;


  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    String mensajeUsuario =	messageSource.getMessage("mensaje.invalido", null, LocaleContextHolder.getLocale());

    String mensajeDesarrollador = ex.getCause()!=null? ex.getCause().toString() :ex.toString() ;

    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST , request);
  }

  //org.springframework.dao.EmptyResultDataAccessException:
  //personalizado

  @ExceptionHandler({EmptyResultDataAccessException.class})
  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex ,WebRequest request) {

    String mensajeUsuario=messageSource.getMessage("recurso.no-encontrado",null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador=ex.toString();
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, new HttpHeaders(), HttpStatus.NOT_FOUND , request);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<Object> handleDataIntegrityViolationException( DataIntegrityViolationException ex ,WebRequest request ){

    String mensajeUsuario=messageSource.getMessage("recurso.operacion-no-permitida",null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador= ExceptionUtils.getRootCauseMessage(ex);// ex.toString();
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, new HttpHeaders(), HttpStatus.BAD_REQUEST , request);

  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    //Aqui creare una lista de errores para retornar ya que puedo tener muchos campos
    //Que esten validados por JPA en mis modelos

    List<Error> errores = crearListaDeErrores(ex.getBindingResult());

    return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST, request);
  }



  private List<Error> crearListaDeErrores(BindingResult bindingResult){

    List<Error> errores= new ArrayList<>();

    for(FieldError fieldError : bindingResult.getFieldErrors()) {
      String mensajeUsuario=messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String mensajeDesarrollador=fieldError.toString();
      errores.add(new Error(mensajeUsuario,mensajeDesarrollador));
    }



    return errores;
  }


  public static class Error {
    private String mensajeUsuario;
    private String mensajeDesarrollador;

    public Error(String mensajeUsuario, String mensajeDesarrollador) {
      super();
      this.mensajeUsuario = mensajeUsuario;
      this.mensajeDesarrollador = mensajeDesarrollador;
    }

    public String getMensajeUsuario() {
      return mensajeUsuario;
    }

    public String getMensajeDesarrollador() {
      return mensajeDesarrollador;
    }

  }

}
