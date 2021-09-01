package com.example.atm_machine.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.atm_machine.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountController accountController;

  @Test
  public void checkBalance() {
    //when
    Mockito.when(accountService.checkBalance("123", "4567")).thenReturn("1020");
    //then
    final String result = accountService.checkBalance("123", "4567");
    //assert
    Mockito.verify(accountService, Mockito.times(1)).checkBalance("123", "4567");
    Assertions.assertEquals(result, "1020");
  }

  @Test
  public void withdrawBalance() {
    final Map<Integer, Integer> qualifiedNotes = new HashMap<>();
    qualifiedNotes.put(50, 2);
    //when
    Mockito.when(accountService.withdrawBalance("123", 200L, "4567")).thenReturn(qualifiedNotes);
    //then
    final Map<Integer, Integer> result = accountService.withdrawBalance("123", 200L, "4567");
    //assert
    Mockito.verify(accountService, Mockito.times(1)).withdrawBalance("123", 200L, "4567");
    Assertions.assertEquals(result, qualifiedNotes);
  }
}
