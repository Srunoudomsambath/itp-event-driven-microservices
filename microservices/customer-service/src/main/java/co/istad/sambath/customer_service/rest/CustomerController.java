package co.istad.sambath.customer_service.rest;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.customer_service.application.CustomerService;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerResponse;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberRequest;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/customer")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {

        log.info("createCustomer: {}", createCustomerRequest);
        return customerService.createCustomer(createCustomerRequest);
    }

    @PutMapping("/{customerId}/phone-number")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChangePhoneNumberResponse  changePhoneNumber(
                                        @PathVariable UUID customerId,
                                        @Valid @RequestBody ChangePhoneNumberRequest changePhoneNumberRequest) {
        log.info("changePhoneNumber: {}", changePhoneNumberRequest);
        return customerService.changePhoneNumber(customerId,changePhoneNumberRequest);
    }

}
