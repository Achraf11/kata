package com.sg.kata.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.kata.account.entity.Account;

/**
 * Account repository
 * 
 * @author Charaf
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumero(String numero);
}
