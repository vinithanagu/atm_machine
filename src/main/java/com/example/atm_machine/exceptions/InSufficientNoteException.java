package com.example.atm_machine.exceptions;

public class InSufficientNoteException extends RuntimeException{
  public InSufficientNoteException(final String message) {
    super(message);
  }
}
