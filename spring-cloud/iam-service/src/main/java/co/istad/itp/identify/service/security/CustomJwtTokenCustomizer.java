package co.istad.itp.identify.service.security;

import co.istad.itp.identify.service.domain.User;
import co.istad.itp.identify.service.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Configuration
public class CustomJwtTokenCustomizer {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                Authentication principal = context.getPrincipal();

                if (principal.getPrincipal() instanceof CustomUserDetails userDetails) {
                    context.getClaims().claims(claims -> {
                        claims.put("email", userDetails.getEmail());
                        claims.put("family_name", userDetails.getFamilyName());
                        claims.put("given_name", userDetails.getGivenName());
                        claims.put("phone_number", userDetails.getPhoneNumber());
                        claims.put("gender", userDetails.getGender());
                        claims.put("birthdate", userDetails.getDob().toString());
                        claims.put("picture", userDetails.getProfileImage());
                        claims.put("cover_image", userDetails.getCoverImage());
                        claims.put("roles", userDetails.getRoles());
                        claims.put("permissions", userDetails.getPermissions());
                    });
                }
            }
        };
    }
}