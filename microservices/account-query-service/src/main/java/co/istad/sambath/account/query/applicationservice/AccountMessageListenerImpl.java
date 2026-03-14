package co.istad.sambath.account.query.applicationservice;


import co.istad.sambath.account.query.applicationservice.mapper.AccountAppDataMapper;
import co.istad.sambath.account.query.applicationservice.ports.input.message.listener.AccountMessageListener;
import co.istad.sambath.account.query.applicationservice.ports.input.service.AccountQueryService;
import co.istad.sambath.account.query.applicationservice.ports.output.repository.AccountQueryRepository;
import co.istad.sambath.account.query.domain.entity.Account;
import co.istad.sambath.common.domain.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountMessageListenerImpl implements AccountMessageListener {

    private final AccountQueryRepository accountQueryRepository;
    private final AccountAppDataMapper accountAppDataMapper;

    @Override
    public void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent) {

        Account account = accountAppDataMapper.accountCreatedEventToAccount(accountCreatedEvent);
        accountQueryRepository.save(account)
                .doOnSuccess(data ->log.info("Account created event successfully: {}", accountCreatedEvent.accountId()))
                .doOnError(error -> log.error("Error saving account", error))
                .subscribe();  // Since Kafka listener is a regular (non-reactive) thread
    }
}
