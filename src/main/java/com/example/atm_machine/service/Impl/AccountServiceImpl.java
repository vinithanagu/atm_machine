package com.example.atm_machine.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atm_machine.entity.AccountEntity;
import com.example.atm_machine.entity.Denominations;
import com.example.atm_machine.exceptions.InSufficientBalanceException;
import com.example.atm_machine.exceptions.InSufficientNoteException;
import com.example.atm_machine.exceptions.NotValidPinNumberException;
import com.example.atm_machine.repository.ATMBalanceRepository;
import com.example.atm_machine.repository.AccountRepository;
import com.example.atm_machine.repository.DenominationsRepository;
import com.example.atm_machine.service.AccountService;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  private final ATMBalanceRepository atmBalanceRepository;

  private final DenominationsRepository denominationsRepository;

  private List<List<Integer>> allDenominations;

  @Override
  public String checkBalance(final String pinNumber, final String accountNumber) {
    final var accountEntity = checkValidAccountNumberAndPinNumber(pinNumber, accountNumber);
    return accountEntity.getOpeningBalance().toString();
  }

  @Override
  @Transactional
  public Map<Integer, Integer> withdrawBalance(final String pinNumber, final Long amount, final String accountNumber) {
    allDenominations = new ArrayList<>();
    final Map<Integer, Integer> result = new HashMap<>();
    final var accountEntity = checkValidAccountNumberAndPinNumber(pinNumber, accountNumber);
    final var atmBalance = atmBalanceRepository.findAll().get(0).getBalance();
    final List<Denominations> notes = denominationsRepository.findAll();

    final List<Integer> qualifiedNotes = new ArrayList<>();
    if (amount <= atmBalance && amount <= accountEntity.getOpeningBalance()) {
      final var set = notes.stream()
          .sorted(Comparator.comparing(Denominations::getNotes))
          .map(x -> Collections.nCopies(x.getCount(), x.getNotes()))
          .flatMap(List::stream)
          .collect(Collectors.toList());
      isSubsetSum(set, set.size(), amount, qualifiedNotes);
      if (allDenominations.isEmpty()) {
        throw new InSufficientNoteException("No Sufficient Notes Available");
      }
      final List<Integer> dispersedNotes = allDenominations.get(0);
      for (int i = 0; i < dispersedNotes.size(); ++i) {
        final int item = dispersedNotes.get(i);

        if (result.containsKey(item)) {
          result.put(item, result.get(item) + 1);
        }
        else {
          result.put(item, 1);
        }
      }
    }
    else {
      throw new InSufficientBalanceException("InSufficient balance in the account : {}" + pinNumber);
    }
    accountRepository.save(AccountEntity.builder()
        .id(accountEntity.getId())
        .openingBalance(amount - accountEntity.getOpeningBalance())
        .build());
    return result;
  }

  private AccountEntity checkValidAccountNumberAndPinNumber(final String pinNumber, final String accountNumber) {
    return accountRepository.findByPinAndAccountNumber(pinNumber, accountNumber)
        .orElseThrow(() -> new NotValidPinNumberException(
            "Pin or Account number is not valid :" + pinNumber + "," + accountNumber));
  }

  private void isSubsetSum(final List<Integer> notes, final int n, final Long amount,
      final List<Integer> qualifiedNotes) {
    if (amount == 0) {
      allDenominations.add(qualifiedNotes);
      return;
    }
    if (n == 0) {
      return;
    }
    isSubsetSum(notes, n - 1, amount, qualifiedNotes);
    final List<Integer> ql1 = new ArrayList<>(qualifiedNotes);
    ql1.add(notes.get(n - 1));
    isSubsetSum(notes, n - 1, amount - (notes.get(n - 1)), ql1);
  }

}
