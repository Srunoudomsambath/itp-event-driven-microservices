package co.istad.sambath.account.query.applicationservice.dto;

import java.util.UUID;

public record AccountQueryResponse(

        UUID accountId,
        String accountNumber
) {
}
