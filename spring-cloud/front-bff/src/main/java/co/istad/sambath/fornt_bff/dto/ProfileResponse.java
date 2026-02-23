package co.istad.sambath.fornt_bff.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProfileResponse(

        String username,
        String email,
        String familyName,
        String givenName,
        String phoneNumber,
        String gender,
        String birthdate,
        String picture,
        String coverImage,
        Set<String> roles,
        Set<String> permission

){
}