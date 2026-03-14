package co.istad.sambath.account.client.adapter;

import co.istad.sambath.account.application.dto.create.CustomerResponse;
import co.istad.sambath.account.application.ports.output.client.CustomerServiceClient;
import co.istad.sambath.account.rest.exception.CustomerNotFoundException;
import co.istad.sambath.account.rest.exception.CustomerServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceClientImpl implements CustomerServiceClient {

    private final RestTemplate restTemplate;

    @Override
    public CustomerResponse getCustomerById(String customerId) {

        try {

            return restTemplate.getForObject(
                    "http://CUSTOMER/api/customers/{customerId}",
                    CustomerResponse.class,
                    customerId
            );

        } catch (HttpClientErrorException.NotFound ex) {

            log.warn("Customer not found [{}]", customerId);
            throw new CustomerNotFoundException(customerId);

        } catch (Exception e) {

            log.error("Customer service unavailable for [{}]: {}", customerId, e.getMessage());
            throw new CustomerServiceUnavailableException("Cannot reach customer service", e);

        }
    }
}