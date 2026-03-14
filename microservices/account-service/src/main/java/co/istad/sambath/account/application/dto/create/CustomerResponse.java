package co.istad.sambath.account.application.dto.create;


import co.istad.sambath.common.domain.valueObject.CustomerName;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        CustomerName name,
        String phoneNumber
) {
}
