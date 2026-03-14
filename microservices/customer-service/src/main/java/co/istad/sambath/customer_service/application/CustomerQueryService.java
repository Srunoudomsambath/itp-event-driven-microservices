package co.istad.sambath.customer_service.application;

import co.istad.sambath.customer_service.application.dto.query.CustomPageResponse;
import co.istad.sambath.customer_service.application.projection.GetCustomerQuery;
import co.istad.sambath.customer_service.application.query.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerQueryService {

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(UUID customerId);
//    Page<CustomerResponse> getCustomersPagination(int page, int size);

    CustomPageResponse getCustomersPagination(int page, int size);

    List<?> getCustomerHistory(UUID customerId);

}
