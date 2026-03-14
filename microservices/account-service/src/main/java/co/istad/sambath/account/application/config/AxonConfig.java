package co.istad.sambath.account.application.config;

import co.istad.sambath.account.client.interceptor.CreateAccountCommandInterceptor;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AxonConfig {

    private final CreateAccountCommandInterceptor interceptor;

    @Autowired
    public void registerInterceptors(CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(interceptor);
    }

}
