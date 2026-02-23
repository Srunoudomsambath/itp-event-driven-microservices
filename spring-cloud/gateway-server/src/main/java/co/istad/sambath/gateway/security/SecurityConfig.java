package co.istad.sambath.gateway.security;


import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http) {

        http.authorizeExchange(exchange -> exchange
//                .pathMatchers("/account/public/**").permitAll()
                .anyExchange().permitAll()
        );

        http.csrf(csrfSpec -> csrfSpec.disable());
        http.formLogin(formLoginSpec -> formLoginSpec.disable());
        http.logout(logoutSpec -> logoutSpec.disable());
        http.httpBasic(httpBasicSpec -> httpBasicSpec.disable());

        http.oauth2ResourceServer(oauth2->oauth2
                .jwt(Customizer.withDefaults()));
//        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }

}
