package co.istad.sambat.pipeline_service.client.dto;

public record UserResponse(
        Integer id,
        String username,
        String email,
        String phone,
        String website
) {
}
