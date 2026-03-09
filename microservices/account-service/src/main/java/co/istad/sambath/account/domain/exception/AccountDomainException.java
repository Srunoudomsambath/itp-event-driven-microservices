package co.istad.sambath.account.domain.exception;

import co.istad.sambath.common.domain.exception.DomainException;

public class AccountDomainException extends DomainException {

    public AccountDomainException(String message) {
        super(message);
    }
    public AccountDomainException(String message, Throwable throwable) {
        super(message, throwable);
    }



}
