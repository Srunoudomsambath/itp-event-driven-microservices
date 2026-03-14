package co.istad.sambath.account.application.ports.output.client;


import co.istad.sambath.account.application.dto.create.CustomerResponse;

public interface CustomerServiceClient {

    CustomerResponse getCustomerById(String customerId);

}