package co.istad.itp.identify.service.security;

import co.istad.itp.identify.service.domain.Permission;
import co.istad.itp.identify.service.domain.Role;
import co.istad.itp.identify.service.domain.User;
import co.istad.itp.identify.service.features.permission.PermissionRepository;
import co.istad.itp.identify.service.features.role.RoleRepository;
import co.istad.itp.identify.service.features.user.UserRepository;
import co.istad.itp.identify.service.oauth2.JpaRegisteredClientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityInit {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @PostConstruct
    public void init() {
        // Initialize Permissions first
        initPermissions();

        // Initialize Roles with Permissions
        initRoles();

        // Initialize Users with Roles
        initUsers();
    }

    private void initPermissions() {
        if (permissionRepository.count() == 0) {
            Permission readPermission = new Permission();
            readPermission.setName("READ");
            permissionRepository.save(readPermission);

            Permission writePermission = new Permission();
            writePermission.setName("WRITE");
            permissionRepository.save(writePermission);

            Permission deletePermission = new Permission();
            deletePermission.setName("DELETE");
            permissionRepository.save(deletePermission);

            Permission updatePermission = new Permission();
            updatePermission.setName("UPDATE");
            permissionRepository.save(updatePermission);

            log.info("Permissions have been initialized");
        }
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            // Create ADMIN role with all permissions
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Set<Permission> adminPermissions = new HashSet<>();
            adminPermissions.add(permissionRepository.findByName("READ").orElseThrow());
            adminPermissions.add(permissionRepository.findByName("WRITE").orElseThrow());
            adminPermissions.add(permissionRepository.findByName("UPDATE").orElseThrow());
            adminPermissions.add(permissionRepository.findByName("DELETE").orElseThrow());
            adminRole.setPermissions(adminPermissions);
            roleRepository.save(adminRole);

            // Create USER role with limited permissions
            Role userRole = new Role();
            userRole.setName("USER");
            Set<Permission> userPermissions = new HashSet<>();
            userPermissions.add(permissionRepository.findByName("READ").orElseThrow());
            userPermissions.add(permissionRepository.findByName("WRITE").orElseThrow());
            userRole.setPermissions(userPermissions);
            roleRepository.save(userRole);

            log.info("Roles have been initialized with permissions");
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUuid(UUID.randomUUID().toString());
            user.setUsername("sambath");
            user.setPassword(passwordEncoder.encode("qwer"));
            user.setEmail("it.chhaya@gmail.com");
            user.setDob(LocalDate.of(1998, 9, 9));
            user.setGender("Male");
            user.setProfileImage("https://www.exstad.tech/scholars/sambath.png");
            user.setCoverImage("https://media.licdn.com/dms/image/v2/D4D16AQEiFoWdAnUN1A/profile-displaybackgroundimage-shrink_200_800/profile-displaybackgroundimage-shrink_200_800/0/1714840400902?e=2147483647&v=beta&t=xGtBWYo6cigGPobtpnW9zoI8b2BTd_SRqZuSws7Ypzg");
            user.setFamilyName("Chan");
            user.setGivenName("Chhaya");
            user.setPhoneNumber("077459947");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setIsEnabled(true);

            // Assign roles to user - fetch from database
            Set<Role> roles = new HashSet<>();
//            roleRepository.findByName("ADMIN").ifPresent(roles::add);
            roleRepository.findByName("USER").ifPresent(roles::add);
            user.setRoles(roles);

            userRepository.save(user);

            // Log to verify roles were assigned
            log.info("User has been saved: {}", user.getId());
            log.info("User roles: {}", user.getRoles().stream()
                    .map(Role::getName)
                    .toList());
        }
    }

    @PostConstruct
    void initOAuth2Bff() {
        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofDays(3))
                .reuseRefreshTokens(false)
                .refreshTokenTimeToLive(Duration.ofDays(5))
                .build();

        ClientSettings clientSettings = ClientSettings.builder()
                .requireProofKey(true)
                .requireAuthorizationConsent(false)
                .build();

        var itpStandard = RegisteredClient.withId("itp-standard")
                .clientId("itp-standard")
                .clientSecret(passwordEncoder.encode("qwerqwer"))
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID);
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:9090/login/oauth2/code/itp-standard");
                    uris.add("http://localhost:9090");
                    uris.add("http://localhost:9999/login/oauth2/code/itp-standard");
                    uris.add("http://localhost:9999");
                    uris.add("https://cstad.edu.kh/");
                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:9090");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                    grantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
                    grantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        var itpFrontBff = RegisteredClient.withId("itp-frontbff")
                .clientId("itp-frontbff")
                .clientSecret(passwordEncoder.encode("qwerqwer"))
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID);
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:9990/login/oauth2/code/itp-frontbff");
                    uris.add("http://localhost:9990");
                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:9990");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        var itpAdminFrontBff = RegisteredClient.withId("itp-admin-frontbff")
                .clientId("itp-admin-frontbff")
                .clientSecret(passwordEncoder.encode("qwerqwer"))
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID);
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:9991/login/oauth2/code/itp-admin-frontbff");
                    uris.add("http://localhost:9991");
                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:9991");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        RegisteredClient registeredClient = jpaRegisteredClientRepository.findByClientId("itp-standard");
        RegisteredClient registeredClient2 = jpaRegisteredClientRepository.findByClientId("itp-frontbff");
        RegisteredClient registeredClient3 = jpaRegisteredClientRepository.findByClientId("itp-admin-frontbff");

        if (registeredClient == null) {
            jpaRegisteredClientRepository.save(itpStandard);
        }
        if (registeredClient2 == null) {
            jpaRegisteredClientRepository.save(itpFrontBff);
        }
        if (registeredClient3 == null) {
            jpaRegisteredClientRepository.save(itpAdminFrontBff);
        }
    }
}