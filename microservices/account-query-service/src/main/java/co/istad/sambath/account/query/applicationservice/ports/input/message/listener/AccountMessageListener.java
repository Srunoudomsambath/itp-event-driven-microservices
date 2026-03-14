package co.istad.sambath.account.query.applicationservice.ports.input.message.listener;

import co.istad.sambath.common.domain.event.AccountCreatedEvent;

public interface AccountMessageListener {

    void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent);
}
