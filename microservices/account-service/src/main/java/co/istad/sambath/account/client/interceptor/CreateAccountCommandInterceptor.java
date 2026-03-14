package co.istad.sambath.account.client.interceptor;

import co.istad.sambath.account.application.ports.output.client.CustomerServiceClient;
import co.istad.sambath.account.application.dto.create.CustomerResponse;
import co.istad.sambath.account.domain.command.CreateAccountCommand;
import co.istad.sambath.account.rest.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateAccountCommandInterceptor
        implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerServiceClient CustomerServiceClient;  // only one dependency now

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {

            if (!(command.getPayload() instanceof CreateAccountCommand createCmd)) {
                return command;
            }

            log.info("Intercepting CreateAccountCommand — validating customerId={}",
                    createCmd.customerId());

            // Only validate what the aggregate CANNOT know — external customer existence
            CustomerResponse customer = CustomerServiceClient
                    .getCustomerById(createCmd.customerId().toString());

            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found: "+ createCmd.customerId());
            }

            return command.andMetaData(Map.of(
                    "customerId",  customer.customerId(),
                    "validatedAt", Instant.now().toString()
            ));
        };
    }
}