package co.istad.sambath.account.domain.command;

import co.istad.sambath.common.domain.valueObject.AccountId;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record FreezeAccountCommand(

        @TargetAggregateIdentifier
        AccountId accountId,
        String remark,
        String requestBy
) {
}
