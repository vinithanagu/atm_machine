package com.example.atm_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atm_machine.entity.DenominationsEntity;

@Repository
public interface DenominationsRepository extends JpaRepository<DenominationsEntity, Long> {

}
