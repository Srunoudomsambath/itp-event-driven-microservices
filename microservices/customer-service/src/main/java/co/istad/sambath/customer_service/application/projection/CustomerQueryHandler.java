package co.istad.sambath.customer_service.application.projection;


import co.istad.sambath.customer_service.application.CustomerQueryService;
import co.istad.sambath.customer_service.application.dto.query.CustomPageResponse;
import co.istad.sambath.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.sambath.customer_service.application.query.CustomerResponse;
import co.istad.sambath.customer_service.data.entity.CustomerEntity;
import co.istad.sambath.customer_service.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

// It will work and handle when axon query gateway request come
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryHandler {

    private final CustomerQueryService customerQueryService;
    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;
                                        // query type
//    @QueryHandler
//    public List<CustomerResponse> handle(GetCustomerQuery getCustomerQuery) {
//        return customerQueryService.getAllCustomers();
//    }


    @QueryHandler
    public List<CustomerResponse> handle(GetCustomerQuery getCustomerQuery) {

        List<CustomerEntity> customers = customerRepository.findAll();

        return customers
                .stream()
                .map(customerApplicationMapper::customerEntityToCustomerResponse)
                .toList();
    }


//    @QueryHandler
//    public List<CustomerResponse> handle(GetCustomerQuery getCustomerQuery) {
//
//        List<CustomerEntity> customers = customerRepository.findAll();
//
//        return customers
//                .stream()
//                .map(customer -> CustomerResponse.builder()
//                        .customerId(customer.getCustomerId())
//                        .name(customer.getName())
//                        .email(customer.getEmail())
//                        .phone(customer.getPhone())
//                        .dob(customer.getDob())
//                        .build())
//                .toList();
//    }


    @QueryHandler
    public CustomerResponse handle(GetCustomerByIdQuery query) {
        CustomerEntity entity = customerRepository.findById(query.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + query.getCustomerId()));
        return customerApplicationMapper.customerEntityToCustomerResponse(entity);
    }





//    @QueryHandler
//    public Page<CustomerResponse> handlePagination(GetCustomerPaginationQuery query) {
//        return customerQueryService.getCustomersPagination(
//                query.getPageNumber(),
//                query.getPageSize()
//        );
//    }


//@QueryHandler
//public Page<CustomerResponse> handlePagination(GetCustomerPaginationQuery getCustomerQuery){
//
//    Pageable pageable = PageRequest.of(
//            getCustomerQuery.getNumber(),
//            getCustomerQuery.getSize(),
//            Sort.by(Sort.Direction.ASC, "dob")
//    );
//
//    Page<CustomerEntity> customers = customerRepository.findAll(pageable);
//
//    return customers
//            .map(customerApplicationMapper::customerEntityToCustomerResponse);
//}





    @QueryHandler
    public CustomPageResponse handle(GetCustomerPaginationQuery getCustomerQuery){
        Pageable pageRequest = PageRequest.of(getCustomerQuery.getNumber(),  getCustomerQuery.getSize(),
                Sort.by(Sort.Direction.DESC, "dob"));
        Page<CustomerEntity> customerEntities = customerRepository.findAll(pageRequest);
        return new CustomPageResponse(customerEntities.map(
                customerApplicationMapper::customerEntityToCustomerResponse
        ));
    }







}
