package co.istad.sambath.customer_service.application.projection;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetCustomerPaginationQuery {
    private int number;
    private int size;
}