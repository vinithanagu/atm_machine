package com.example.atm_machine.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.atm_machine.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping(path = "/checkBalance")
  @ResponseStatus(value = HttpStatus.OK)
  @ApiResponse(responseCode = "200", description = "Success.")
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  public ResponseEntity checkBalance(@RequestParam final String pinNumber, @RequestParam final String accountNumber) {
    return ResponseEntity.ok(accountService.checkBalance(pinNumber, accountNumber));
  }

  @GetMapping(path = "/withdraw")
  @ResponseStatus(value = HttpStatus.OK)
  @ApiResponse(responseCode = "200", description = "Success.")
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  public ResponseEntity withDrawBalance(@RequestParam final String pinNumber, @RequestParam final String accountNumber,
      @RequestParam final Long amount) {
    return ResponseEntity.ok(accountService.withdrawBalance(pinNumber, amount, accountNumber));
  }

}
