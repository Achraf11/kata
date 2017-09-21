package com.sg.kata.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * Customer entity
 * 
 * @author Charaf
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private final Long id;

    private final String nom;

    private final String prenom;

    @OneToOne(fetch = LAZY)
    private final Account account;
}
