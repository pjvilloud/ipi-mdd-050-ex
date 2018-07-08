package com.ipiecoles.java.mdd050.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe d'erreur customisée permettant de renvoyer un objet JSON
 * comportant les informations de l'erreur au client au lieu
 * d'un simple message qui n'est pas du JSON valide
 */
public class IpiError {

    /**
     * Code HTTP de la réponse 4XX ou 5XX
     */
    private HttpStatus status;

    /**
     * Message précisant l'erreur
     */
    private String message;

    public IpiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
