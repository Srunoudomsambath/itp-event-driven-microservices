package co.istad.sambat.pipeline_service.controller;


import co.istad.sambat.pipeline_service.client.AccountClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequestMapping("/client/account")
@RestController
public class AccountClientController {
    private final AccountClient accountClient;
    private final CircuitBreaker circuitBreaker;
    public AccountClientController(AccountClient accountClient,
                                   CircuitBreakerRegistry registry) {
        this.accountClient = accountClient;
        circuitBreaker = registry.circuitBreaker("account");
    }


    @GetMapping("/secured")
    public Map<String,Object> getSecureData(){
        //return accountClient.getSecuredData();
//        log.debug("getSecureData");
        try {
//            throw new RuntimeException("Error internal");
            return circuitBreaker.executeSupplier(accountClient::getSecuredData);
        } catch (CallNotPermittedException e) {
            return Map.of("data", e.getMessage());
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            return Map.of("data", e.getMessage());
        }
    }
}
