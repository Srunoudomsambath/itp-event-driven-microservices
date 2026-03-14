package co.istad.sambath.account.query.dataaccess.adapter;

import co.istad.sambath.account.query.applicationservice.ports.output.repository.AccountQueryRepository;
import co.istad.sambath.account.query.dataaccess.entity.AccountEntity;
import co.istad.sambath.account.query.dataaccess.mapper.AccountDataAccessMapper;
import co.istad.sambath.account.query.dataaccess.repository.AccountQueryReactiveRepository;
import co.istad.sambath.account.query.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
// inject bean
@Slf4j
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final AccountQueryReactiveRepository accountQueryReactiveRepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Mono<Account> save(Account account) {

        log.info("Attempt to save account {}", account);
        AccountEntity accountEntity = accountDataAccessMapper.accountToAccountEntity(account);
        log.info("Mapped account entity {}", accountEntity);
        return accountQueryReactiveRepository.save(accountEntity)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }

    @Override
    public Mono<Account> findById(UUID accountId) {
        return accountQueryReactiveRepository
                .findById(accountId)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }
}
