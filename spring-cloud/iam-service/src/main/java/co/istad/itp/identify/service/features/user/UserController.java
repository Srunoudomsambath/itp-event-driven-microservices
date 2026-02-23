package co.istad.itp.identify.service.features.user;


import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @PreAuthorize("hasAnyAuthority('user:read:own','user:read:all')")
//    @PreAuthorize("ROLE_USER")
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(Map.of("message", "Find User successfully!"));
    }
}
