package co.istad.sambath.account.query.domain.entity;

import co.istad.sambath.common.domain.valueObject.AccountStatus;
import co.istad.sambath.common.domain.valueObject.AccountTypeCode;
import co.istad.sambath.common.domain.valueObject.Currency;
import co.istad.sambath.common.domain.valueObject.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@ToString
// POJO class
public class Account {
    private UUID accountId;

    private UUID customerId;
    private UUID branchId;

    private String accountNumber;
    private String accountHolder;

    private AccountTypeCode accountTypeCode;
//    private UUID accountTypeId;


//    private Money money;
    private BigDecimal balance;
    private Currency currency;

    private AccountStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;
    private String createdBy;
    private String updatedBy;



}
