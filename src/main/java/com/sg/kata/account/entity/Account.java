package com.sg.kata.account.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Account entity
 * 
 * @author Charaf
 *
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Account {

    @Id
    @GeneratedValue
    private final Long id;

    @NotNull
    private final String numero;

    private final long solde;

    @OneToOne(fetch = FetchType.LAZY)
    private final Customer customer;

    @OneToMany(fetch = FetchType.LAZY)
    private final List<Operation> operations;
}
