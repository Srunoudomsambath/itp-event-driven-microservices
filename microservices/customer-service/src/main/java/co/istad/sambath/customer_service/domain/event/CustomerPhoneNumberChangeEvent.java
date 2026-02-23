package co.istad.sambath.customer_service.domain.event;


import co.istad.sambath.common.domain.valueObject.CustomerId;
import lombok.Builder;

@Builder
public record CustomerPhoneNumberChangeEvent (
    CustomerId customerId,
    String phoneNumber
){
}
