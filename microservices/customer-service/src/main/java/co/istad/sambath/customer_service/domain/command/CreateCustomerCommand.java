package co.istad.sambath.customer_service.domain.command;

import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.valueObject.*;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Builder
public record CreateCustomerCommand(
        @TargetAggregateIdentifier
        // call command Customer id
        // implementation project(':common')
        CustomerId customerId,
        CustomerName customerName,
        CustomerGender customerGender,
        CustomerEmail customerEmail,
        LocalDate dob,
        Kyc kyc,
        Address address,
        Contact contact,
        CustomerSegmentId customerSegmentId
) {
}
