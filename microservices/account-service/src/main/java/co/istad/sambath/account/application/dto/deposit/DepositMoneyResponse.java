package co.istad.sambath.account.application.dto.deposit;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DepositMoneyResponse(
//
//        UUID transactionId,
//        String accountNumber,
//        Money previousBalance,
//        Money newBalance,
//        String message
        UUID transactionId,
        UUID accountId,
        String message
) {
}
