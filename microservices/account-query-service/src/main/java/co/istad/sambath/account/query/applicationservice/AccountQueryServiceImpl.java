package co.istad.sambath.account.query.applicationservice;

import co.istad.sambath.account.query.applicationservice.dto.AccountQueryResponse;
import co.istad.sambath.account.query.applicationservice.mapper.AccountAppDataMapper;
import co.istad.sambath.account.query.applicationservice.ports.input.service.AccountQueryService;
import co.istad.sambath.account.query.applicationservice.ports.output.repository.AccountQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountAppDataMapper accountAppDataMapper;
    private final AccountQueryRepository accountQueryRepository;

    @Override
    public Mono<AccountQueryResponse> getByAccountId(UUID accountId) {
        return accountQueryRepository.findById(accountId)
                .map(accountAppDataMapper::accountToAccountQueryResponse);
    }
}
