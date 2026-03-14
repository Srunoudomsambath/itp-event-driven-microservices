package co.istad.sambath.account.application;

import co.istad.sambath.account.application.dto.create.CreateAccountRequest;
import co.istad.sambath.account.application.dto.create.CreateAccountResponse;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyRequest;
import co.istad.sambath.account.application.dto.deposit.DepositMoneyResponse;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountRequest;
import co.istad.sambath.account.application.dto.freeze.FreezeAccountResponse;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyRequest;
import co.istad.sambath.account.application.dto.withdraw.WithdrawMoneyResponse;
import co.istad.sambath.account.application.mapper.AccountApplicationMapper;
import co.istad.sambath.account.application.ports.input.service.AccountService;
import co.istad.sambath.account.domain.command.CreateAccountCommand;
import co.istad.sambath.account.domain.command.DepositMoneyCommand;
import co.istad.sambath.account.domain.command.FreezeAccountCommand;
import co.istad.sambath.account.domain.command.WithdrawMoneyCommand;
import co.istad.sambath.account.domain.exception.AccountDomainException;
import co.istad.sambath.common.domain.valueObject.AccountId;
import co.istad.sambath.common.domain.valueObject.AccountStatus;
import co.istad.sambath.common.domain.valueObject.Money;
import co.istad.sambath.common.domain.valueObject.TransactionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final  AccountApplicationMapper accountApplicationMapper;
    private final CommandGateway commandGateway;


    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        // map create request to create command
        CreateAccountCommand createAccountCommand = accountApplicationMapper
                .createAccountRequestToCreateAccountCommand(new AccountId(UUID.randomUUID()),createAccountRequest);

        log.info("CreateAccountCommand: {}", createAccountCommand);

        // 2. Invoke and handle Axon command gateway
        AccountId result = commandGateway.sendAndWait(createAccountCommand);
        log.info("CreateAccountCommand result: {}", result);

        return CreateAccountResponse.builder()
                .accountId(createAccountCommand.accountId().value())
                .message("Account saved successfully")
                .build();

    }

//    @Override
//    public DepositMoneyResponse depositMoney(String accountNumber, DepositMoneyRequest request) {
//
//        // 1. Fetch the account from the read-side (projection/entity) to resolve the AccountId
//        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
//                .orElseThrow(() -> new AccountDomainException("Account not found: " + accountNumber));
//
//        Money amount = new Money(request.amount(), request.currency());
//        Money previousBalance = new Money(account.getInitialBalance().amount(), account.getInitialBalance().currency());
//
//        // 2. Build the command
//        DepositMoneyCommand command = DepositMoneyCommand.builder()
//                .accountId(new AccountId(account.getAccountId()))
//                .transactionId(new TransactionId(UUID.randomUUID()))
//                .amount(amount)
//                .remark(request.remark())
//                .build();
//
//        log.info("DepositMoneyCommand: {}", command);
//
//        // 3. Send command via Axon gateway (fire-and-wait)
//        commandGateway.sendAndWait(command);
//
//        // 4. Compute expected new balance for the response (optimistic — based on command amount)
//        Money newBalance = previousBalance.add(amount);
//
//        return DepositMoneyResponse.builder()
//                .transactionId(command.transactionId().value())
//                .accountNumber(accountNumber)
//                .previousBalance(previousBalance)
//                .newBalance(newBalance)
//                .message("Deposit successful")
//                .build();
//    }

    @Override
    public DepositMoneyResponse depositMoney(UUID accountId, DepositMoneyRequest request) {

        DepositMoneyCommand command = DepositMoneyCommand.builder()
                .accountId(new AccountId(accountId))
                .transactionId(new TransactionId(UUID.randomUUID()))
                .amount(new Money(request.amount(),request.currency()))
                .remark(request.remark())
                .customerId(request.customerId())
                .build();

        log.info("DepositMoneyCommand: {}", command);

        // 3. Send command via Axon gateway (fire-and-wait)
        try {
            commandGateway.sendAndWait(command);
        } catch (AggregateNotFoundException e) {
            throw new AccountDomainException("Account not found: " + accountId);
        }


        return DepositMoneyResponse.builder()
                .transactionId(command.transactionId().value())
                .accountId(accountId)
                .message("Deposit successful")
                .build();
    }
    @Override
    public WithdrawMoneyResponse withdrawMoney(UUID accountId, WithdrawMoneyRequest request) {

        WithdrawMoneyCommand command = WithdrawMoneyCommand.builder()
                .accountId(new AccountId(accountId))       // from path variable
                .transactionId(new TransactionId(UUID.randomUUID()))
                .amount(new Money(request.amount(), request.currency()))
                .remark(request.remark())
                .customerId(request.customerId())
                .build();

        log.info("WithdrawMoneyCommand: {}", command);

        try {
            commandGateway.sendAndWait(command);
        } catch (AggregateNotFoundException e) {
            throw new AccountDomainException("Account not found: " + accountId);
        }

        return WithdrawMoneyResponse.builder()
                .transactionId(command.transactionId().value())
                .accountId(accountId)
                .message("Withdrawal successful")
                .build();
    }


    @Override
    public FreezeAccountResponse freezeAccount(UUID accountId, FreezeAccountRequest request) {

        FreezeAccountCommand command = FreezeAccountCommand.builder()
                .accountId(new AccountId(accountId))
                .remark(request.remark())
                .requestBy(request.requestBy())
                .build();

        log.info("FreezeAccountCommand: {}", command);

        try {
            commandGateway.sendAndWait(command);
        } catch (AggregateNotFoundException e) {
            throw new AccountDomainException("Account not found: " + accountId);
        }

        return FreezeAccountResponse.builder()
                .accountId(accountId)
                .previousStatus(AccountStatus.ACTIVE)   // we know it was ACTIVE (validated in aggregate)
                .newStatus(AccountStatus.FREEZE)
                .message("Account frozen successfully")
                .build();
    }
}
