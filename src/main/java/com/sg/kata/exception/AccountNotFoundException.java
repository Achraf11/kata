package com.sg.kata.exception;

/**
 * Created by ghazala on 30/11/16.
 */
public class AccountNotFoundException extends RuntimeException {

    private String message;

    public AccountNotFoundException(String message) {
        this.message = message;
    }
}
