package co.istad.sambath.account.application.dto.withdraw;

import lombok.Builder;

import java.util.UUID;

@Builder
public record WithdrawMoneyResponse(
        UUID transactionId,
        UUID accountId,
        String message
) {}