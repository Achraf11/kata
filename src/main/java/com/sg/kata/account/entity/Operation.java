package com.sg.kata.account.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Operation entity
 * 
 * @author Charaf
 *
 */
@Entity
@Data
@Builder
public class Operation {

    @Id
    @GeneratedValue
    private final Long id;

    private final Date date;

    private final Long amount;

    private final OperationType operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    private final Account account;
}
