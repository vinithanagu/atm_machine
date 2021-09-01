package com.example.atm_machine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping(path = "/health")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity isHealthy() {
    return ResponseEntity.ok("Health is Up");
  }
}
