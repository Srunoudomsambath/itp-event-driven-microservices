package co.istad.sambath.account.application.ports.input.service;

import co.istad.sambath.account.application.dto.create.CreateAccountRequest;
import co.istad.sambath.account.application.dto.create.CreateAccountResponse;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyRequest;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyResponse;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountRequest;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountResponse;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyRequest;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyResponse;

import java.util.UUID;

public interface AccountService {

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    DepositMoneyResponse depositMoney(UUID accountId,DepositMoneyRequest depositMoneyRequest);

    WithdrawMoneyResponse withdrawMoney(UUID accountId, WithdrawMoneyRequest request);

    FreezeAccountResponse freezeAccount(UUID accountId, FreezeAccountRequest request);

//    DepositMoneyResponse depositMoney(String accountNumber,DepositMoneyRequest depositMoneyRequest);

}
