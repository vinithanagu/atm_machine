package com.example.atm_machine.exceptions;

public class InvalidAmountException extends RuntimeException{
  public InvalidAmountException(final String message) {
    super(message);
  }
}
