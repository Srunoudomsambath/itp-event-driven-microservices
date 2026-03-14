package co.istad.sambath.account.application.dto.create;

import co.istad.sambath.common.domain.valueObject.AccountTypeCode;
import co.istad.sambath.common.domain.valueObject.BranchId;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.Money;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(

        @NotNull
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountTypeCode accountTypeCode,
        BranchId branchId,
        Money initialBalance

) {
}
