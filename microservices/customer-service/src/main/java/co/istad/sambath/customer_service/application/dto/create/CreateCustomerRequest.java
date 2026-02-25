package co.istad.sambath.customer_service.application.dto.create;

import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.valueObject.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCustomerRequest(

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

        //
        @NotNull
        Contact contact,

        @NotNull
        String phoneNumber,

        @NotNull
        CustomerSegmentId customerSegmentId

) {
}
