package co.istad.sambath.fornt_bff.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ReactiveClientRegistrationRepository clientRegistrationRepository
    ) {

        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/store/**").authenticated()
                        .anyExchange().permitAll()
                );
                http.csrf(ServerHttpSecurity.CsrfSpec::disable);
                http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
                http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
                http.oauth2Login(Customizer.withDefaults());
                http.logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)));

        return http.build();
    }

    // OIDC logout
    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }
    // Client logout
    private ServerLogoutSuccessHandler serverLogoutSuccessHandler() {

        RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler =
                new RedirectServerLogoutSuccessHandler();

        final String DEFAULT_LOGOUT_SUCCESS_URL = "/";

        URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);

        redirectServerLogoutSuccessHandler.setLogoutSuccessUrl(logoutSuccessUrl);

        return redirectServerLogoutSuccessHandler;
    }
}
