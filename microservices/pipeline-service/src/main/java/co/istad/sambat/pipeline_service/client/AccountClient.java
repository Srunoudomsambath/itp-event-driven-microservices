package co.istad.sambat.pipeline_service.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;
@HttpExchange("/api/v1/account")
public interface AccountClient {
    @GetExchange
    Map<String, Object> getSecuredData();
}
