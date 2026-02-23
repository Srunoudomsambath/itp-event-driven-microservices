package co.istad.sambath.customer_service.application.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ChangePhoneNumberResponse(

        @NotNull
        UUID customerId,

        @NotBlank
        String phoneNumber,

        @NotBlank
        String message
) {
}
