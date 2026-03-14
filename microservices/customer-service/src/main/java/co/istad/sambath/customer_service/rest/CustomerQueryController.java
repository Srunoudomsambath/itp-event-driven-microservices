package co.istad.sambath.customer_service.rest;


import co.istad.sambath.customer_service.application.CustomerQueryService;
import co.istad.sambath.customer_service.application.dto.query.CustomPageResponse;
import co.istad.sambath.customer_service.application.projection.GetCustomerQuery;
import co.istad.sambath.customer_service.application.query.CustomerResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerQueryController {

    // no more get data from service we get from queryGateway
    private final QueryGateway queryGateway;
    private final CustomerQueryService customerQueryService;


//    @GetMapping
//    public List<CustomerResponse> getAllCustomers(){
//        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
//        return queryGateway
//                .query(getCustomerQuery, ResponseTypes.multipleInstancesOf(CustomerResponse.class))
//                .join(); // when result response   List <CustomerResponse>  Provided: CompletableFuture <List<CustomerResponse>>
//
//    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerQueryService.getAllCustomers();
    }

//    @GetMapping("/pagination")
//    public Page<CustomerResponse> getAllCustomersPagination(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return customerQueryService.getCustomersPagination(page, size);
//    }

    @GetMapping("/pagination")
    public CustomPageResponse getAllCustomersPagination(
            @RequestParam(defaultValue = "0", required = false) int number,
            @RequestParam(defaultValue = "5", required = false) int size
    ){
        return customerQueryService.getCustomersPagination(number, size);
    }

    @GetMapping("/{customerId}/history")
    public  List<?> getCustomerHistory(@PathVariable UUID customerId){
        return customerQueryService.getCustomerHistory(customerId);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable UUID customerId) {
        return customerQueryService.getCustomerById(customerId);
    }
}
