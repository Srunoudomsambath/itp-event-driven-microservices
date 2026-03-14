package co.istad.sambath.account.application.mapper;


import co.istad.sambath.account.application.dto.create.CreateAccountRequest;
import co.istad.sambath.account.domain.command.CreateAccountCommand;
import co.istad.sambath.common.domain.event.AccountCreatedEvent;
import co.istad.sambath.common.domain.valueObject.AccountId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountApplicationMapper {

    CreateAccountCommand createAccountRequestToCreateAccountCommand(AccountId accountId, CreateAccountRequest createAccountRequest);

}








