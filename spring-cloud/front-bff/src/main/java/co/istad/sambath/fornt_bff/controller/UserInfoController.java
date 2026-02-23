package co.istad.sambath.fornt_bff.controller;

import co.istad.sambath.fornt_bff.dto.ProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserInfoController {

    @GetMapping("/me")
    public ProfileResponse me(@AuthenticationPrincipal OidcUser oidcUser) {


        List<String> rolesList = oidcUser.getAttribute("roles");
        Set<String> roles = rolesList != null ? new HashSet<>(rolesList) : new HashSet<>();

        List<String> permissionsList = oidcUser.getAttribute("permissions");
        Set<String> permissions = permissionsList != null ? new HashSet<>(permissionsList) : new HashSet<>();

        return ProfileResponse.builder()
                .username(oidcUser.getName())
                .email(oidcUser.getEmail())
                .familyName(oidcUser.getFamilyName())
                .givenName(oidcUser.getGivenName())
                .phoneNumber(oidcUser.getPhoneNumber())
                .gender(oidcUser.getGender())
                .birthdate(oidcUser.getBirthdate())
                .picture(oidcUser.getPicture())
                .coverImage(oidcUser.getAttribute("cover_image"))
                .roles(roles)
                .permission(permissions)
                .build();
    }
}