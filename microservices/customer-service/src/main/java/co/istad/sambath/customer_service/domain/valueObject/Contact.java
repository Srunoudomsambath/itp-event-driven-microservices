package co.istad.sambath.customer_service.domain.valueObject;

import java.util.UUID;

public record Contact(
        UUID contactId,
        String type,
        String number

) {
}
