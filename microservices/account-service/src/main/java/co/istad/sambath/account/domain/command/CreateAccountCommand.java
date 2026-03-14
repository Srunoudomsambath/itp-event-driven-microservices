package co.istad.sambath.account.domain.command;

import co.istad.sambath.common.domain.valueObject.*;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record CreateAccountCommand(

        @TargetAggregateIdentifier
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountTypeCode accountTypeCode,
        BranchId branchId,
        Money initialBalance

) {
}



