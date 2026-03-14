package co.istad.sambath.account.application.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateAccountResponse(

        @NotNull
        UUID accountId,

        @NotBlank
        String message
) {
}
