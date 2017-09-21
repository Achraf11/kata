package com.sg.kata.account.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Customer;
import com.sg.kata.account.entity.Operation;
import com.sg.kata.account.repository.OperationRepositoryImpl;

import java.util.Date;
import java.util.List;

import static com.sg.kata.account.entity.OperationType.DEPOSIT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Charaf
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OperationRepositoryTest {

    @Autowired
    private OperationRepositoryImpl operationRepository;
    @Autowired
    private TestEntityManager testEntityManager;

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
        testEntityManager.persist(account);
        testEntityManager.persist(customer);
        testEntityManager.persist(depot);
        testEntityManager.persist(retrait);

    }

    @Test
    public void should_load_all_operations_by_account() {
        List<Operation> result = operationRepository.findByAccount(account);

        assertThat(result).isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(depot, retrait);
    }

}