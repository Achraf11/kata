package com.sg.kata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Amount;
import com.sg.kata.account.entity.Operation;
import com.sg.kata.account.entity.OperationType;
import com.sg.kata.account.service.AccountService;
import com.sg.kata.account.service.OperationService;

import java.util.List;

/**
 * Created by ghazala on 30/11/16.
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationService operationService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.PUT,
            path = "/api/accounts/{accountNumero}/operations/{operationType}")
    public Account update(@PathVariable String accountNumero,
                          @PathVariable OperationType operationType,
                          @RequestBody Amount amount) {
        return accountService.updateSolde(amount, accountNumero, operationType);
    }

    @RequestMapping(method = RequestMethod.GET,
            path = "/api/accounts/{accountNumero}/history")
    public List<Operation> getAccountHistory(@PathVariable String accountNumero) {
        return operationService.fetchBy(accountNumero);
    }
}
