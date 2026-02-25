package co.istad.sambath.customer_service.application.mapper;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.sambath.customer_service.data.entity.CustomerEntity;
import co.istad.sambath.customer_service.domain.command.CreateCustomerCommand;
import co.istad.sambath.customer_service.domain.event.CustomerCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper  {

    CreateCustomerCommand createCustomerRequestToCreateCustomerCommand(CustomerId customerId, CreateCustomerRequest createCustomerRequest);

    @Mapping(source = "customerId.value",target = "customerId")
    CustomerEntity customerCreatedEventToCustomerEntity(CustomerCreatedEvent customerCreatedEvent);
}








