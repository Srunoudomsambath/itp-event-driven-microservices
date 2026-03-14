package co.istad.sambath.account.rest.exception;

import co.istad.sambath.common.domain.exception.DomainException;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}