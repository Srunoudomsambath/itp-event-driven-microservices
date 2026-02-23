package co.istad.sambath.fornt_bff.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        Boolean isAuthenticated,
        String name
) {
}