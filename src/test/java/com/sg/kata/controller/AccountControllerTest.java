package com.sg.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Amount;
import com.sg.kata.account.entity.Customer;
import com.sg.kata.account.entity.Operation;
import com.sg.kata.account.service.AccountService;
import com.sg.kata.account.service.OperationService;
import com.sg.kata.controller.AccountController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static com.google.common.collect.ImmutableList.of;
import static com.sg.kata.account.entity.OperationType.DEPOSIT;
import static com.sg.kata.account.entity.OperationType.WITHDRAWL;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charaf
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private OperationService operationService;

    private ObjectMapper mapper = new ObjectMapper();
    private Amount amount;
    private Account accountWithdraw;
    private Account accountDeposit;
    private Operation depot;
    private Operation retrait;

    @Before
    public void init() {
        amount = new Amount(50L);
        accountWithdraw = Account.builder()
                .id(1L)
                .customer(Customer.builder()
                        .id(1L).build()
                )
                .solde(1050)
                .numero("1000236").build();
        accountDeposit = Account.builder()
                .id(1L)
                .customer(Customer.builder()
                        .id(1L).build()
                )
                .solde(1100)
                .numero("1000236").build();
        depot = Operation.builder()
                .id(1L)
                .amount(50L)
                .date(new Date(1111L))
                .account(accountWithdraw)
                .operationType(DEPOSIT).build();
        retrait = Operation.builder()
                .id(2L)
                .amount(300L)
                .date(new Date(22222L))
                .account(accountWithdraw)
                .operationType(WITHDRAWL).build();
    }

    @Test
    public void should_make_deposit_by_account_numero() throws Exception {
        doReturn(accountDeposit).when(accountService).updateSolde(amount, "1000236", DEPOSIT);

        mockMvc.perform(put("/api/accounts/1000236/operations/DEPOSIT/")
                .content(mapper.writeValueAsString(amount))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(
                                "{'id':1, 'customer': {'id':1}, 'solde': 1100, 'numero': '1000236', operations:null}"
                        )
                );
    }

    @Test
    public void should_make_withdraw_by_account_numero() throws Exception {
        doReturn(accountWithdraw).when(accountService).updateSolde(amount, "1000236", WITHDRAWL);

        mockMvc.perform(put("/api/accounts/1000236/operations/WITHDRAWL/")
                .content(mapper.writeValueAsString(amount))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(
                                "{'id':1, 'customer': {'id':1}, 'solde': 1050, 'numero': '1000236'}"
                        )
                );
    }

    @Test
    public void should_fetch_all_account_history() throws Exception {
        doReturn(of(depot, retrait)).when(operationService).fetchBy("1000236");

        mockMvc.perform(get("/api/accounts/1000236/history/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
        .andExpect(content()
            .json("[{'id':1, date:1111, amount:50, operationType:'DEPOSIT', account:{id:1}}," +
                  " {'id':2, date:22222, amount:300, operationType:'WITHDRAWL', account:{id:1}}" +
                  "]")
        );
    }
}
