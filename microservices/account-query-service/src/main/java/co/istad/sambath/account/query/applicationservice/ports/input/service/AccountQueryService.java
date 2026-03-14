package co.istad.sambath.account.query.applicationservice.ports.input.service;


import co.istad.sambath.account.query.applicationservice.dto.AccountQueryResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

// input port for application service
public interface AccountQueryService {

    Mono<AccountQueryResponse> getByAccountId(UUID accountId);

}
