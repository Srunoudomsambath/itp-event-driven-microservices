package co.istad.sambath.account.application.dto.withdraw;


import co.istad.sambath.common.domain.valueObject.Currency;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawMoneyRequest(

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        CustomerId customerId,


        @NotNull(message = "Currency is required")
        Currency currency,

        String remark
) {}