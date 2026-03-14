package co.istad.sambath.account.query.dataaccess.mapper;

import co.istad.sambath.account.query.dataaccess.entity.AccountEntity;
import co.istad.sambath.account.query.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDataAccessMapper {

    // so for this infrastructure we can call the domain model core
    // but domain model can not call this infrastructure
    @Mapping(constant = "true", target = "new")
    AccountEntity accountToAccountEntity(Account account);

    Account accountEntityToAccount(AccountEntity accountEntity);
}
