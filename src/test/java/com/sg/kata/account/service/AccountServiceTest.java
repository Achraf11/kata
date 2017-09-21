package com.sg.kata.account.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Amount;
import com.sg.kata.account.entity.Customer;
import com.sg.kata.account.repository.AccountRepository;
import com.sg.kata.account.service.AccountService;
import com.sg.kata.exception.SoldeInsuffisantException;

import static com.sg.kata.account.entity.OperationType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

/**
 * Service account Test
 * @author Charaf
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Spy
    @InjectMocks
    private AccountService accountService;

    @Test
    public void should_update_account_solde_deposit_case() {
        Customer custom = Customer.builder().id(1L).build();
        Account account = Account.builder()
                .id(1L)
                .numero("1003580")
                .customer(custom)
                .solde(1000L).build();
        Account updatedAccount = Account.builder()
                .id(1L)
                .numero("1003580")
                .customer(custom)
                .solde(1100L).build();
        Amount amount = new Amount(100L);
        doReturn(account).when(accountRepository).findByNumero("1003580");
        doReturn(updatedAccount).when(accountRepository).save(updatedAccount);

        Account result = accountService.updateSolde(amount, "1003580", DEPOSIT);

        assertThat(result).isEqualToComparingFieldByField(updatedAccount);
    }

    @Test
    public void should_update_account_solde_withdrawl_case() {
        Customer custom = Customer.builder().id(1L).build();
        Account account = Account.builder()
                .id(1L)
                .numero("1003580")
                .customer(custom)
                .solde(1000L).build();
        Account updatedAccount = Account.builder()
                .id(1L)
                .numero("1003580")
                .customer(custom)
                .solde(900L).build();
        Amount amount = new Amount(100L);
        doReturn(account).when(accountRepository).findByNumero("1003580");
        doReturn(updatedAccount).when(accountRepository).save(updatedAccount);

        Account result = accountService.updateSolde(amount, "1003580", WITHDRAWL);

        assertThat(result).isEqualToComparingFieldByField(updatedAccount);
    }

    @Test
    public void should_not_authorize_customer_to_make_withdraw_if_his_solde_is_less_then_amount() {
        Customer custom = Customer.builder().id(1L).build();
        Account account = Account.builder()
                .id(1L)
                .numero("1003580")
                .customer(custom)
                .solde(1000L).build();
        doReturn(account).when(accountRepository).findByNumero("1003580");

        assertThatThrownBy(() -> accountService.updateSolde(new Amount(1100L), "1003580", WITHDRAWL))
                .isInstanceOf(SoldeInsuffisantException.class);
    }
}
