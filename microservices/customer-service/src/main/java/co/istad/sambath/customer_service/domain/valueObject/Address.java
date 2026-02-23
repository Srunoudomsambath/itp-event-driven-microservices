package co.istad.sambath.customer_service.domain.valueObject;

import java.util.UUID;

public record Address(
        UUID addressId,
        String line,
        String city,
        String zipCode,
        String country
) {
}
