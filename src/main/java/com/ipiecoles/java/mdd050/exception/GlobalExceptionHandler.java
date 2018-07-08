package com.ipiecoles.java.mdd050.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.net.ConnectException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public IpiError handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new IpiError(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IpiError handleIllegalArgumentException(IllegalArgumentException e) {
        return new IpiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IpiError handlePropertyReferenceException(PropertyReferenceException e) {
        return new IpiError(HttpStatus.BAD_REQUEST, "La propriété " + e.getPropertyName() + " n'existe pas !");
    }

    @ExceptionHandler(EmployeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IpiError handleEmployeException(EmployeException e) {
        return new IpiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public IpiError handleConflictException(ConflictException e) {
        return new IpiError(HttpStatus.CONFLICT, e.getMessage());
    }


    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return "La ressource à laquelle vous essayer d'accéder n'existe pas ou plus !";
    }

    /**
     * Gestion des méthodes non autorisées
     *
     * @param e Exception renvoyée par Spring MVC
     * @return message d'erreur
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return e.getMessage();
    }

    /**
     * Gestion des endpoints inexistants
     *
     * @param ex
     * @return message d'erreur
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return "Pas de endpoint trouvé pour la méthode HTTP " + ex.getHttpMethod() + " et l'url " + ex.getRequestURL();
    }

    /**
     * Gestion des problèmes de types sur les paramètres
     *
     * @param ex
     * @return message d'erreur
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected IpiError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new IpiError(HttpStatus.NOT_FOUND,"Le type du paramètre " + ex.getName() + " est incorrect pour la valeur '" + ex.getValue() + "'");
    }


    /**
     * Gestion des problèmes de connection à la BDD
     *
     * @param ex
     * @return message d'erreur
     */
    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected IpiError handleConnectException(CannotCreateTransactionException ex) {
        return new IpiError(HttpStatus.INTERNAL_SERVER_ERROR,"Problème de connexion à la base de données");
    }
}
