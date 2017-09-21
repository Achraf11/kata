package com.sg.kata.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.kata.account.entity.Account;
import com.sg.kata.account.entity.Operation;

import java.util.List;

/**
 * Operation repository
 * 
 * @author Charaf
 */
 
public interface OperationRepositoryImpl extends JpaRepository<Operation, Long> {

    List<Operation> findByAccount(Account account);
}
