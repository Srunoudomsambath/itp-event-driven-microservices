package co.istad.sambath.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class UnSecureController {

    @Value("${service.name}")
    private String name;

    @Value("${secret.weak-password}")
    private String weakPassword;

    @Value("${secret.strong-password}")
    private String strongPassword;

    @GetMapping
    public Map<String, String> unsecureEndpoint() {
        return Map.of(
                "serviceName", name,
                "weakPassword", weakPassword,
                "strongPassword", strongPassword
        );
    }
}
