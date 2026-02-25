package co.istad.sambath.customer_service.application;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerResponse;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberRequest;
import co.istad.sambath.customer_service.application.dto.update.ChangePhoneNumberResponse;
import co.istad.sambath.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.sambath.customer_service.domain.command.ChangePhoneNumberCommand;
import co.istad.sambath.customer_service.domain.command.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerApplicationMapper customerApplicationMapper;

    // Connect everything
    private final CommandGateway commandGateway;

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {

        // 1. Transfer data from request to command
        CreateCustomerCommand createCustomerCommand = customerApplicationMapper
                .createCustomerRequestToCreateCustomerCommand(new CustomerId(UUID.randomUUID()), createCustomerRequest);
        log.info("CreateCustomerCommand: {}", createCustomerCommand);

        // 2. Invoke and handle Axon command gateway
        CustomerId result = commandGateway.sendAndWait(createCustomerCommand);
        log.info("CreateCustomerCommand result: {}", result);

        return CreateCustomerResponse.builder()
                .customerId(createCustomerCommand.customerId().value())
                .message("Customer saved successfully")
                .build();

    }

    @Override
    public ChangePhoneNumberResponse changePhoneNumber(UUID customerId, ChangePhoneNumberRequest changePhoneNumberRequest) {

        // 1. Transfer data from request to command
        ChangePhoneNumberCommand changePhoneNumberCommand = ChangePhoneNumberCommand.builder()
                .customerId(new CustomerId(customerId))
                .phoneNumber(changePhoneNumberRequest.phoneNumber())
                .build();
        log.info("Change phone number command: {}", changePhoneNumberCommand);

        // Send command (no return expected)
        commandGateway.sendAndWait(changePhoneNumberCommand);

        return ChangePhoneNumberResponse.builder()
                .customerId(customerId)   // use input ID
                .phoneNumber(changePhoneNumberRequest.phoneNumber())
                .message("Change phone number successfully")
                .build();
    }


}
