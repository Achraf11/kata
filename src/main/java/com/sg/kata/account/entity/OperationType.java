package com.sg.kata.account.entity;

/**
 * OperationType entity
 * 
 * @author Charaf
 *
 */
public enum OperationType {
    DEPOSIT("Dépôt"),
    WITHDRAWL("Retrait");

    private String value;

    OperationType(String value) {
        this.value = value;
    }
}
