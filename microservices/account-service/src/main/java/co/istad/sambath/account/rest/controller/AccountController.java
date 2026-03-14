package co.istad.sambath.account.rest.controller;


import co.istad.sambath.account.application.ports.input.service.AccountService;
import co.istad.sambath.account.application.dto.create.CreateAccountRequest;
import co.istad.sambath.account.application.dto.create.CreateAccountResponse;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyRequest;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyResponse;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountRequest;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountResponse;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyRequest;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {

        log.info("createAccount: {}", createAccountRequest);
        return accountService.createAccount(createAccountRequest);
    }

//    @PostMapping("/{accountNumber}/deposit")
//    @ResponseStatus(HttpStatus.OK)
//    public DepositMoneyResponse depositMoney(
//            @PathVariable String accountNumber,
//            @Valid @RequestBody DepositMoneyRequest request) {
//        log.info("depositMoney for account: {}, request: {}", accountNumber, request);
//        return accountService.depositMoney(accountNumber, request);
//    }

    @PostMapping("/{accountId}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public DepositMoneyResponse depositMoney(
            @PathVariable UUID accountId,
            @Valid @RequestBody DepositMoneyRequest request) {
        log.info("depositMoney for account: {}, request: {}", accountId, request);
        return accountService.depositMoney(accountId, request);
    }

    @PostMapping("/{accountId}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public WithdrawMoneyResponse withdrawMoney(
            @PathVariable UUID accountId,
            @Valid @RequestBody WithdrawMoneyRequest request) {
        log.info("withdrawMoney for account: {}", accountId);
        return accountService.withdrawMoney(accountId, request);
    }

    @PatchMapping("/{accountId}/freeze")
    @ResponseStatus(HttpStatus.OK)
    public FreezeAccountResponse freezeAccount(
            @PathVariable UUID accountId,
            @Valid @RequestBody FreezeAccountRequest request) {
        log.info("freezeAccount for account: {}", accountId);
        return accountService.freezeAccount(accountId, request);
    }
}
