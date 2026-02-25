package co.istad.sambath.customer_service.application.listener;


import co.istad.sambath.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.sambath.customer_service.data.entity.CustomerEntity;
import co.istad.sambath.customer_service.data.entity.CustomerSegmentEntity;
import co.istad.sambath.customer_service.data.repository.CustomerRepository;
import co.istad.sambath.customer_service.data.repository.CustomerSegmentRepository;
import co.istad.sambath.customer_service.domain.event.CustomerCreatedEvent;
import co.istad.sambath.customer_service.domain.event.CustomerPhoneNumberChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerListener {

    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;
    private final CustomerSegmentRepository customerSegmentRepository;

    // Log everything on event handler
    // Listen event from EventSourcing when save success
    @EventHandler
    public void on (CustomerPhoneNumberChangeEvent  customerPhoneNumberChangeEvent) {
        log.info("CustomerPhoneNumberChangeEvent: {}",customerPhoneNumberChangeEvent);
        // 1. Find existing customer
        CustomerEntity customerEntity = customerRepository
                .findById(customerPhoneNumberChangeEvent.customerId().value())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer not found"
                        )
                );
        // 2. Update phone number
        customerEntity.setPhoneNumber(customerPhoneNumberChangeEvent.phoneNumber());

        // 3. Save (update)
        customerRepository.save(customerEntity);
    }

    @EventHandler
    public void on (CustomerCreatedEvent customerCreatedEvent) {
        log.info("CustomerCreateEvent: {}",customerCreatedEvent);

        // we map customerCreatedEvent to customerEntity
        CustomerEntity customerEntity = customerApplicationMapper.customerCreatedEventToCustomerEntity(customerCreatedEvent);

        customerEntity.getAddress().setCustomer(customerEntity);
        customerEntity.getContact().setCustomer(customerEntity);
        customerEntity.getKyc().setCustomer(customerEntity);

        CustomerSegmentEntity customerSegmentEntity = customerSegmentRepository
                .findById(customerCreatedEvent.customerSegmentId().customerSegmentId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Segment Not Found"));
        customerEntity.setCustomerSegment(customerSegmentEntity);

        customerRepository.save(customerEntity);
    }

}
