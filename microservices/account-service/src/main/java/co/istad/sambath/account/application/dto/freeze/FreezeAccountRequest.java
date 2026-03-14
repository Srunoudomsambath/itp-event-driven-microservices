package co.istad.sambath.account.application.dto.freeze;


import jakarta.validation.constraints.NotBlank;

public record FreezeAccountRequest(

        @NotBlank(message = "Reason is required")
        String remark,

        @NotBlank(message = "RequestedBy is required")
        String requestBy
) {}