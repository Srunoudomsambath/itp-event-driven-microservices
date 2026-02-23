package co.istad.sambath.customer_service.domain.valueObject;

import java.util.UUID;

public record Kyc(
        UUID kycId,
        String type,
        String number
) {
}
