package com.sg.kata.exception;

/**
 * Created by ghazala on 09/12/16.
 */
public class SoldeInsuffisantException extends RuntimeException {

    private String message;

    public SoldeInsuffisantException(String message) {
        this.message = message;
    }
}
