package co.istad.sambath.customer_service.application.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetCustomerByIdQuery {
    private UUID customerId;
}