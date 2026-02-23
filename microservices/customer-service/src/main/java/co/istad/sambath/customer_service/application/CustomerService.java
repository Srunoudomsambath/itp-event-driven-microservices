package co.istad.sambath.customer_service.application;

import co.istad.sambath.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerResponse;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberRequest;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberResponse;

import java.util.UUID;

public interface CustomerService {


    ChangePhoneNumberResponse changePhoneNumber(UUID customerId, ChangePhoneNumberRequest changePhoneNumberRequest);

    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
}
