package co.istad.sambath.common.domain.event;


// immutable so we make it as record

import co.istad.sambath.common.domain.valueObject.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountCreatedEvent(

        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountTypeCode accountTypeCode,
        BranchId branchId,
        Money initialBalance,
        ZonedDateTime createdAt,
        String createdBy,
        AccountStatus status

) {
}
