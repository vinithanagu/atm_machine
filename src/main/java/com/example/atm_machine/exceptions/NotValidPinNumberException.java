package com.example.atm_machine.exceptions;

public class NotValidPinNumberException extends RuntimeException {
  public NotValidPinNumberException(final String message) {
    super(message);
  }
}
