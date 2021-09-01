package com.example.atm_machine.service;

import java.util.Map;

public interface AccountService {

  String checkBalance(String pinNumber, String accountNumber);

  Map<Integer, Integer> withdrawBalance(String pinNumber, Long amount, String accountNumber);
}
