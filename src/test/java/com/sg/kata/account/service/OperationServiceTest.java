package com.sg.kata.account.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Customer;
import com.sg.kata.account.entity.Operation;
import com.sg.kata.account.repository.AccountRepository;
import com.sg.kata.account.repository.OperationRepositoryImpl;
import com.sg.kata.account.service.OperationService;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static com.sg.kata.account.entity.OperationType.DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * Service operation Test
 * @author Charaf
 */
@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

    @Mock
    private OperationRepositoryImpl operationRepository;
    @Mock
    private AccountRepository accountRepository;

    @Spy
    @InjectMocks
    private OperationService operationService;

    private Customer customer;
    private Operation depot;
    private Operation retrait;
    private Account account;

    @Before
    public void init() {
        customer = Customer.builder().nom("TOTO").prenom("TATA").build();
        account = Account.builder()
                .numero("15230BC554")
                .solde(500L)
                .customer(customer)
                .build();
        depot = Operation.builder()
                .amount(50L)
                .date(new Date(1111L))
                .account(account)
                .operationType(DEPOSIT).build();
        retrait = Operation.builder()
                .amount(300L)
                .date(new Date(22222L))
                .account(account)
                .operationType(DEPOSIT).build();
    }

    @Test
    public void should_fetch_all_operations_by_account() throws Exception {
        doReturn(of(depot, retrait)).when(operationRepository).findByAccount(account);
        doReturn(account).when(accountRepository).findByNumero("15230BC554");

        List<Operation> operations = operationService.fetchBy("15230BC554");

        assertThat(operations).isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(depot, retrait);
    }
}