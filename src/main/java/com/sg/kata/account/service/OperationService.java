package com.sg.kata.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Operation;
import com.sg.kata.account.repository.AccountRepository;
import com.sg.kata.account.repository.OperationRepositoryImpl;

import java.util.List;

/**
 * Created by ghazala on 09/12/16.
 */
@Service
public class OperationService {

    @Autowired
    private OperationRepositoryImpl operationRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Operation> fetchBy(String accountNumero) {
        Account account = accountRepository.findByNumero(accountNumero);
        return operationRepository.findByAccount(account);
    }
}
