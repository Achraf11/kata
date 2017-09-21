package com.sg.kata.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Amount;
import com.sg.kata.account.entity.OperationType;
import com.sg.kata.account.repository.AccountRepository;
import com.sg.kata.exception.AccountNotFoundException;
import com.sg.kata.exception.SoldeInsuffisantException;

import java.util.Optional;

/**
 * Account service
 * 
 * @author Charaf
 *
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private Optional<Account> fetchByNumero(String accountNumero) {
        return Optional.of(accountRepository.findByNumero(accountNumero));
    }

    @Transactional
    public Account updateSolde(Amount amount, String accountNum, OperationType deposit) {
        Account account = fetchByNumero(accountNum)
                .orElseThrow(() -> new AccountNotFoundException("Account not found, please check your account numero"));
        switch (deposit) {
            case DEPOSIT:
                return accountRepository.save(Account.builder().id(account.getId())
                        .solde(account.getSolde() + amount.getValue())
                        .numero(account.getNumero())
                        .customer(account.getCustomer()).build());
            case WITHDRAWL:
                if(amount.getValue() > account.getSolde()) {
                    throw new SoldeInsuffisantException("Votre solde est insuffisant, l'opération est annulée.");
                }
                return accountRepository.save(Account.builder().id(account.getId())
                        .solde(account.getSolde() - amount.getValue())
                        .numero(account.getNumero())
                        .customer(account.getCustomer()).build());
            default:
                throw new IllegalArgumentException("Le type d'opération donné ne correspondant à aucun type.");
        }
    }
}
