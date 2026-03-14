package co.istad.sambath.account.application.dto.deposit;

import co.istad.sambath.common.domain.valueObject.Currency;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositMoneyRequest (

//        AccountId accountId,
//        CustomerId customerId,
//        Money previousBalance,
//        Money newBalance
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        CustomerId customerId,

        @NotNull(message = "Currency is required")
        Currency currency,

        String remark
) {
}
