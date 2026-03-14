package co.istad.sambath.account.rest.exception;


import co.istad.sambath.account.domain.exception.AccountDomainException;
import co.istad.sambath.common.domain.application.exception.ErrorResponse;
import co.istad.sambath.common.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class AccountException {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleAccountDomainException(DomainException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timeStamp(ZonedDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(ZonedDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerServiceUnavailableException.class)
    public ResponseEntity<?> handleCustomerServiceUnavailable(CustomerServiceUnavailableException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(ex.getMessage())
                .timeStamp(ZonedDateTime.now())
                .build(),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

}
