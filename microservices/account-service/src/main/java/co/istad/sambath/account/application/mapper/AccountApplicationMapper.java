package co.istad.sambath.account.application.mapper;


import co.istad.sambath.account.application.dto.create.CreateAccountRequest;
import co.istad.sambath.account.data.entity.AccountEntity;
import co.istad.sambath.account.domain.command.CreateAccountCommand;
import co.istad.sambath.account.domain.event.AccountCreatedEvent;
import co.istad.sambath.common.domain.valueObject.AccountId;
import co.istad.sambath.common.domain.valueObject.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountApplicationMapper {

    CreateAccountCommand createAccountRequestToCreateAccountCommand(AccountId accountId, CreateAccountRequest createAccountRequest);

    // spring can not understand about the uuid
    @Mapping(source = "accountId.value", target = "accountId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "branchId.branchId", target = "branchId")
    AccountEntity accountCreatedEventToEntity(AccountCreatedEvent event);

}








