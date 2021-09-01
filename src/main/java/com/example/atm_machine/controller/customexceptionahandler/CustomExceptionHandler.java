package com.example.atm_machine.controller.customexceptionahandler;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.atm_machine.exceptions.NotValidPinNumberException;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<String> handle(final NotValidPinNumberException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<String> handle(final Exception e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>("Unexpected error in performing operation", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
