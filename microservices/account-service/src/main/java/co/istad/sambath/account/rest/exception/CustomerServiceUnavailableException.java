package co.istad.sambath.account.rest.exception;

import co.istad.sambath.common.domain.exception.DomainException;

// Exception for when the Customer service is unavailable (HTTP 503)
public class CustomerServiceUnavailableException extends DomainException {

    public CustomerServiceUnavailableException(String message) {
        super(message);
    }

    public CustomerServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}