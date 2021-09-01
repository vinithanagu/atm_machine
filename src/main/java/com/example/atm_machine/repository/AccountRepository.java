package com.example.atm_machine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atm_machine.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {

  Optional<AccountEntity> findByPinAndAccountNumber(String pin, String accountNumber);
}
