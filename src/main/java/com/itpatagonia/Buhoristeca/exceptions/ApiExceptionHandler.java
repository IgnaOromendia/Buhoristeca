package com.itpatagonia.Buhoristeca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            RuntimeException.class,
            BookAlreadyRegisteredException.class,
            BookCopiesNotAvailableException.class,
            ClientAlreadyRegisteredException.class,
            ClientAlreadyHasALoanException.class,
            ClientDoesNotHaveThisBookOnLoanException.class,
            BookIsNotActiveException.class,
            ClientIsNotActiveException.class
    })
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            BookNotFoundException.class,
            AuthorNotFoundException.class,
            BookCopyNotFoundException.class,
            StateNotFoundException.class,
            ClientNotFoundException.class,
            GenresNotFoundException.class,
            LanguageNotFoundException.class,
            LoanNotFoundException.class,
            PublisherNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<String> handleBookNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
