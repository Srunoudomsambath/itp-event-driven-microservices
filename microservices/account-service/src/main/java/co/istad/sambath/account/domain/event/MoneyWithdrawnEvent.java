package co.istad.sambath.account.domain.event;

import co.istad.sambath.common.domain.valueObject.AccountId;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.Money;
import co.istad.sambath.common.domain.valueObject.TransactionId;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record MoneyWithdrawnEvent(

        AccountId accountId,
        TransactionId transactionId,
        Money amount,
        String remark,
        CustomerId customerId,
        Money newBalance,
        ZonedDateTime createdAt
) {
}
