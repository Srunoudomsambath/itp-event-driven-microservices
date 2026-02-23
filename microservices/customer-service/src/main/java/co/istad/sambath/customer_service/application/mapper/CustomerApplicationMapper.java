package co.istad.sambath.customer_service.application.mapper;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.sambath.customer_service.domain.command.CreateCustomerCommand;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper  {

    CreateCustomerCommand createCustomerRequestToCreateCustomerCommand(CustomerId customerId, CreateCustomerRequest createCustomerRequest);
}








