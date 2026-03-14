package co.istad.sambath.customer_service.application;

import co.istad.sambath.customer_service.application.dto.query.CustomPageResponse;
import co.istad.sambath.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.sambath.customer_service.application.projection.GetCustomerByIdQuery;
import co.istad.sambath.customer_service.application.projection.GetCustomerPaginationQuery;
import co.istad.sambath.customer_service.application.projection.GetCustomerQuery;
import co.istad.sambath.customer_service.application.query.CustomerResponse;
import co.istad.sambath.customer_service.data.entity.CustomerEntity;
import co.istad.sambath.customer_service.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements  CustomerQueryService{

    private final QueryGateway queryGateway;
    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;
    private final EventStore eventStore; // to get the event from the event store

//    @Override
//    public List<CustomerResponse> getAllCustomers() {
//
//        List<CustomerEntity> customers = customerRepository.findAll();
//        return customers
//                .stream()
//                .map(customerApplicationMapper::customerEntityToCustomerResponse)
//                .toList();
//    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();

        return queryGateway
                .query(getCustomerQuery, ResponseTypes.multipleInstancesOf(CustomerResponse.class))
                .join();
    }


    @Override
    public CustomerResponse getCustomerById(UUID customerId) {
        GetCustomerByIdQuery query = new GetCustomerByIdQuery(customerId);
        return queryGateway
                .query(query, ResponseTypes.instanceOf(CustomerResponse.class))
                .join();
    }

//    @Override
//    public Page<CustomerResponse> getCustomersPagination(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<CustomerEntity> customerPage = customerRepository.findAll(pageable);
//
//        return customerPage
//                .map(customerApplicationMapper::customerEntityToCustomerResponse);
//    }



    // dara
    // PageImpl get more a lot of data
//    @Override
//    public Page<CustomerResponse> getCustomersPagination(int page, int size) {
//
//        GetCustomerPaginationQuery getCustomerQuery = new GetCustomerPaginationQuery();
//        getCustomerQuery.setNumber(page);
//        getCustomerQuery.setSize(size);
//
//        List<CustomerResponse> customers = queryGateway
//                .query(getCustomerQuery, ResponseTypes.multipleInstancesOf(CustomerResponse.class))
//                .join();
//
//        return new PageImpl<>(customers, PageRequest.of(page, size), customers.size());
//    }



    // vannda
    @Override
    public CustomPageResponse getCustomersPagination(int page, int size){
        GetCustomerPaginationQuery getCustomerQuery = GetCustomerPaginationQuery.builder()
                .number(page)
                .size(size)
                .build();
        return queryGateway
                .query(getCustomerQuery, ResponseTypes.instanceOf(CustomPageResponse.class))
                .join();
    }


    @Override
    public List<?> getCustomerHistory(UUID customerId) {

        // get customer history by aggregate identifier
        // can not store value object in domain event entry


        return  eventStore.readEvents(customerId.toString())
                .asStream()
                .map(Message::getPayload)
                .toList();
    }
}

