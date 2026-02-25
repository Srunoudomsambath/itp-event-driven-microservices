package co.istad.sambath.customer_service.domain.aggregate;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import co.istad.sambath.common.domain.valueObject.CustomerSegmentId;
import co.istad.sambath.customer_service.domain.command.ChangePhoneNumberCommand;
import co.istad.sambath.customer_service.domain.command.CreateCustomerCommand;
import co.istad.sambath.customer_service.domain.event.CustomerCreatedEvent;
import co.istad.sambath.customer_service.domain.event.CustomerPhoneNumberChangeEvent;
import co.istad.sambath.customer_service.domain.valueObject.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;

// Entity of DDD
// It can not set value so no Setter
// Axion
@Slf4j
@Aggregate
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CustomerAggregate {

    //
    @AggregateIdentifier
    private CustomerId customerId;

    private CustomerName name;
    private CustomerGender gender;
    private CustomerEmail email;
    private LocalDate dob;
    private Kyc kyc;
    private Address address;
    private Contact contact;
    private String phoneNumber;
    private CustomerSegmentId  customerSegmentId;
    private List<String> failureMessages;


    // Domain Logic for creating customer Write logic here
    // It's not business logic (business logic include domain logic)
    // We write Constructor CommandHandler required for first event
    // Produced event
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
        // Perform domain logic
        // Validate email
        // Validate phone number

        // Publish event -> CustomerCreatedEvent
        CustomerCreatedEvent customerCreatedEvent = CustomerCreatedEvent.builder()
                .customerId(createCustomerCommand.customerId())
                .name(createCustomerCommand.name())
                .gender(createCustomerCommand.gender())
                .email(createCustomerCommand.email())
                .dob(createCustomerCommand.dob())
                .kyc(createCustomerCommand.kyc())
                .address(createCustomerCommand.address())
                .contact(createCustomerCommand.contact())
                .phoneNumber(createCustomerCommand.phoneNumber())
                .customerSegmentId(createCustomerCommand.customerSegmentId())
                .build();
        AggregateLifecycle.apply(customerCreatedEvent);
    }

    @CommandHandler
    public void handle(ChangePhoneNumberCommand changePhoneNumberCommand) {
        log.info("Handle ChangePhoneNumberCommand: {}", changePhoneNumberCommand);

        CustomerPhoneNumberChangeEvent customerPhoneNumberChangeEvent = CustomerPhoneNumberChangeEvent.builder()
                .customerId(changePhoneNumberCommand.customerId())
                .phoneNumber(changePhoneNumberCommand.phoneNumber())
                .build();

        AggregateLifecycle.apply(customerPhoneNumberChangeEvent);
    }

    @EventSourcingHandler
        public void on (CustomerCreatedEvent customerCreatedEvent) {
        this.customerId = customerCreatedEvent.customerId();
        this.name = customerCreatedEvent.name();
        this.gender = customerCreatedEvent.gender();
        this.email = customerCreatedEvent.email();
        this.dob = customerCreatedEvent.dob();
        this.kyc = customerCreatedEvent.kyc();
        this.address = customerCreatedEvent.address();
        this.contact = customerCreatedEvent.contact();
        this.phoneNumber = customerCreatedEvent.phoneNumber();
        this.customerSegmentId = customerCreatedEvent.customerSegmentId();
    }

    @EventSourcingHandler
    public void on (CustomerPhoneNumberChangeEvent customerPhoneNumberChangeEvent) {
        this.customerId = customerPhoneNumberChangeEvent.customerId();
        this.phoneNumber = customerPhoneNumberChangeEvent.phoneNumber();
    }


}
