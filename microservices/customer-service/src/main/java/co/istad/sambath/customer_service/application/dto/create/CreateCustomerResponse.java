package co.istad.sambath.customer_service.application.dto.create;

import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.valueObject.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CreateCustomerResponse(

        @NotNull
        UUID customerId,

        @NotBlank
        String message
) {
}
