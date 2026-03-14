package co.istad.sambath.account.query.applicationservice.mapper;


import co.istad.sambath.account.query.applicationservice.dto.AccountQueryResponse;
import co.istad.sambath.account.query.domain.entity.Account;
import co.istad.sambath.common.domain.event.AccountCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountAppDataMapper {

    AccountQueryResponse accountToAccountQueryResponse(Account account);

    @Mapping(source = "accountId.value", target = "accountId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "branchId.branchId", target = "branchId")
    @Mapping(source = "initialBalance.amount", target = "balance")
    @Mapping(source = "initialBalance.currency", target = "currency")
    Account accountCreatedEventToAccount(AccountCreatedEvent accountCreatedEvent);
}
