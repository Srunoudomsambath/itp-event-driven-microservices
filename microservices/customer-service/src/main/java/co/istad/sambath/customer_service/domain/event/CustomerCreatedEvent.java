package co.istad.sambath.customer_service.domain.event;


// immutable so we make it as record

import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.valueObject.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerCreatedEvent(
        CustomerId customerId,
        CustomerName customerName,
        CustomerGender customerGender,
        CustomerEmail customerEmail,
        LocalDate dob,
        Kyc kyc,
        Address address,
        Contact contact,
        CustomerSegmentId customerSegmentId
) {
}
