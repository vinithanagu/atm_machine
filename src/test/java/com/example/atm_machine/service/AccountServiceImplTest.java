package com.example.atm_machine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.atm_machine.entity.ATMBalanceEntity;
import com.example.atm_machine.entity.AccountEntity;
import com.example.atm_machine.entity.DenominationsEntity;
import com.example.atm_machine.exceptions.InSufficientBalanceException;
import com.example.atm_machine.exceptions.InSufficientNoteException;
import com.example.atm_machine.exceptions.NotValidPinNumberException;
import com.example.atm_machine.repository.ATMBalanceRepository;
import com.example.atm_machine.repository.AccountRepository;
import com.example.atm_machine.repository.DenominationsRepository;
import com.example.atm_machine.service.Impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private ATMBalanceRepository atmBalanceRepository;

  @Mock
  private DenominationsRepository denominationsRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Test
  void checkBalanceTest() {
    final AccountEntity accountEntity = AccountEntity.builder()
        .accountNumber("45Gb")
        .pin("1234")
        .openingBalance(600L)
        .build();
    Mockito.when(accountRepository.findByPinAndAccountNumber("1234", "45Gb")).thenReturn(Optional.of(accountEntity));

    final String result = accountService.checkBalance("1234", "45Gb");

    Assertions.assertEquals("600", result);
  }

  @Test
  void checkBalanceIncorrectPinNumberTest() {
    Mockito.when(accountRepository.findByPinAndAccountNumber("1234", "45Gb")).thenReturn(Optional.empty());

    Assertions.assertThrows(NotValidPinNumberException.class, () -> accountService.checkBalance("1234", "45Gb"));
  }

  @Test
  void withdrawBalanceTest() {
    final AccountEntity accountEntity = AccountEntity.builder()
        .accountNumber("45Gb")
        .pin("1234")
        .openingBalance(600L)
        .build();
    final List<DenominationsEntity> denominations = new ArrayList<>();
    denominations.add(DenominationsEntity.builder().Notes(20).count(5).build());
    denominations.add(DenominationsEntity.builder().Notes(50).count(3).build());
    final ATMBalanceEntity atmBalanceEntity = ATMBalanceEntity.builder().balance(1000).build();

    Mockito.when(accountRepository.findByPinAndAccountNumber("1234", "45Gb")).thenReturn(Optional.of(accountEntity));
    Mockito.when(denominationsRepository.findAll()).thenReturn(denominations);
    Mockito.when(atmBalanceRepository.findAll()).thenReturn(Collections.singletonList(atmBalanceEntity));

    final Map<Integer, Integer> result = accountService.withdrawBalance("1234", 200L, "45Gb");
    Assertions.assertNotNull(result);
    System.out.println(result);
  }

  @Test
  void withdrawInSufficientNotesTest() {
    final AccountEntity accountEntity = AccountEntity.builder()
        .accountNumber("45Gb")
        .pin("1234")
        .openingBalance(600L)
        .build();
    final List<DenominationsEntity> denominations = new ArrayList<>();
    denominations.add(DenominationsEntity.builder().Notes(20).count(5).build());
    denominations.add(DenominationsEntity.builder().Notes(50).count(3).build());
    final ATMBalanceEntity atmBalanceEntity = ATMBalanceEntity.builder().balance(1000).build();

    Mockito.when(accountRepository.findByPinAndAccountNumber("1234", "45Gb")).thenReturn(Optional.of(accountEntity));
    Mockito.when(denominationsRepository.findAll()).thenReturn(denominations);
    Mockito.when(atmBalanceRepository.findAll()).thenReturn(Collections.singletonList(atmBalanceEntity));

    Assertions.assertThrows(InSufficientNoteException.class,
        () -> accountService.withdrawBalance("1234", 202L, "45Gb"));
  }

  @Test
  void withdrawInSufficientBalanceTest() {
    final AccountEntity accountEntity = AccountEntity.builder()
        .accountNumber("45Gb")
        .pin("1234")
        .openingBalance(100L)
        .build();
    final ATMBalanceEntity atmBalanceEntity = ATMBalanceEntity.builder().balance(1000).build();

    Mockito.when(accountRepository.findByPinAndAccountNumber("1234", "45Gb")).thenReturn(Optional.of(accountEntity));
    Mockito.when(atmBalanceRepository.findAll()).thenReturn(Collections.singletonList(atmBalanceEntity));

    Assertions.assertThrows(InSufficientBalanceException.class,
        () -> accountService.withdrawBalance("1234", 200L, "45Gb"));
  }

}
