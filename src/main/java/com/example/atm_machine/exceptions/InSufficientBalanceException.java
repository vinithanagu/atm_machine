package com.example.atm_machine.exceptions;

public class InSufficientBalanceException extends RuntimeException {
  public InSufficientBalanceException(final String message) {
    super(message);
  }
}
