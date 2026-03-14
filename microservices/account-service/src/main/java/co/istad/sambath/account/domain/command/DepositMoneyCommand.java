package co.istad.sambath.account.domain.command;

import co.istad.sambath.common.domain.valueObject.AccountId;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.Money;
import co.istad.sambath.common.domain.valueObject.TransactionId;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record DepositMoneyCommand (

        @TargetAggregateIdentifier
        AccountId accountId,
        CustomerId customerId,
        TransactionId transactionId,
        Money amount,
        String remark // The remark is transaction metadata, meaning extra information describing the transaction.
){
}
