package com.ipiecoles.java.mdd050.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePropertyReferenceException(PropertyReferenceException e) {
        return "La propriété " + e.getPropertyName() + " n'existe pas !";
    }

    @ExceptionHandler(EmployeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeException(EmployeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflictException(ConflictException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return "La ressource à laquelle vous essayer d'accéder n'existe pas ou plus !";
    }

}
