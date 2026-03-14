package co.istad.sambath.customer_service.application.projection;


// Read operation
// can be data like pagination

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetCustomerQuery {
    private int number;
    private int size;
}