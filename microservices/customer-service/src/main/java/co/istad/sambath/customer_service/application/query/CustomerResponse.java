package co.istad.sambath.customer_service.application.query;

import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.valueObject.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponse(

        UUID customerId,

        @NotNull
        CustomerName name,

        @NotNull
        CustomerEmail email,

        @NotNull
        LocalDate dob,

        @NotNull
        CustomerGender gender,

        @NotNull
        Kyc kyc,

        @NotNull
        Address address,

        @NotNull
        Contact contact,

        @NotNull
        String phoneNumber,

        // response as object customerSegment
        @NotNull
        CustomerSegmentResponse customerSegment

) {
}
