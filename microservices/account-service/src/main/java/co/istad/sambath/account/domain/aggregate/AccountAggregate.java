package co.istad.sambath.account.domain.aggregate;


import co.istad.sambath.account.domain.command.CreateAccountCommand;
import co.istad.sambath.account.domain.command.DepositMoneyCommand;
import co.istad.sambath.account.domain.event.AccountCreatedEvent;
import co.istad.sambath.account.domain.event.MoneyDepositedEvent;
import co.istad.sambath.account.domain.exception.AccountDomainException;
import co.istad.sambath.account.domain.validate.AccountValidate;
import co.istad.sambath.account.domain.valueObject.AccountStatus;
import co.istad.sambath.account.domain.valueObject.AccountTypeCode;
import co.istad.sambath.account.domain.valueObject.Currency;
import co.istad.sambath.account.domain.valueObject.Money;
import co.istad.sambath.common.domain.valueObject.AccountId;
import co.istad.sambath.common.domain.valueObject.BranchId;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private AccountId accountId;

    private String accountNumber;
    private String accountHolder;
    private CustomerId customerId;
    private AccountTypeCode accountTypeCode;
    private BranchId branchId;
    private Money balance;

    private AccountStatus status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String createdBy;
    private String updatedBy;


    private void validateAccountType(AccountTypeCode accountTypeCode) {

        if(accountTypeCode != AccountTypeCode.SAVING){
            throw new AccountDomainException("Account type code can be only SAVING");
        }
    }

    // when create account you need to have more than 10 usd
    private void validateInitialBalance(Money initialBalance) {
        Money minInitialBalance = new Money(BigDecimal.valueOf(10), Currency.USD);
        if(initialBalance.isLessthan(minInitialBalance)){
            throw new AccountDomainException("Create new account is required 10$");
        }
    }


    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {

        log.info("CreateAccountCommand: {}", command);

        // validate account number
        AccountValidate.validateAccountNumber(command.accountNumber());

        // validate account type code

        // validate balance

        // create event object then apply lifecycle
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .accountId(command.accountId())
                .accountNumber(command.accountNumber())
                .accountHolder(command.accountHolder())
                .customerId(command.customerId())
                .accountTypeCode(command.accountTypeCode())
                .branchId(command.branchId())
                .initialBalance(command.initialBalance())
                .build();
        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.accountId();
        this.accountNumber = event.accountNumber();
        this.accountHolder = event.accountHolder();
        this.customerId = event.customerId();
        this.accountTypeCode = event.accountTypeCode();
        this.branchId = event.branchId();
        this.balance = event.initialBalance();
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }


    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        log.info("DepositMoneyCommand: {}", command);

        Money newBalance = this.balance.add(command.amount());

        MoneyDepositedEvent event = MoneyDepositedEvent.builder()
                .accountId(command.accountId())
                .transactionId(command.transactionId())
                .amount(command.amount())
                .newBalance(newBalance)
                .remark(command.remark())
                .createdAt(ZonedDateTime.now())
                .build();

        AggregateLifecycle.apply(event);
    }

    // Each event should update only the fields it actually changes.
    @EventSourcingHandler
    public void on(MoneyDepositedEvent event) {
        this.balance = event.newBalance(); // adding to existing balance.
        this.updatedAt = event.createdAt();

    }
}
