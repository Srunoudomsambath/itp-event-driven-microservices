package co.istad.sambath.account.query.applicationservice.ports.output.repository;


import co.istad.sambath.account.query.domain.entity.Account;
import reactor.core.publisher.Mono;

import java.util.UUID;

// Output port for data access technology
public interface AccountQueryRepository {


    // Save account
    // Our port is reactive so use Mono
    Mono<Account> save(Account account);

    Mono<Account> findById(UUID accountId);
}
