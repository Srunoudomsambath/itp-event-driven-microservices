package co.istad.sambath.account.application.dto.freeze;

import co.istad.sambath.common.domain.valueObject.AccountStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FreezeAccountResponse(
        UUID accountId,
        AccountStatus previousStatus,
        AccountStatus newStatus,
        String message
) {}