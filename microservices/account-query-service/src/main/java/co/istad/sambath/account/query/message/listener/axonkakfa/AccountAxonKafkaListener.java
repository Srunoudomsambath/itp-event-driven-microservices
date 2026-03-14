package co.istad.sambath.account.query.message.listener.axonkakfa;


import co.istad.sambath.account.query.applicationservice.ports.input.message.listener.AccountMessageListener;
import co.istad.sambath.common.domain.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ProcessingGroup("account-group")
public class AccountAxonKafkaListener {


    private final AccountMessageListener accountMessageListener;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("Account Created Event: {}", accountCreatedEvent);
        accountMessageListener.onAccountCreatedEvent(accountCreatedEvent);
    }
}
