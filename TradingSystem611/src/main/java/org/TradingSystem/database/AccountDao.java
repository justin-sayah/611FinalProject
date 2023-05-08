package org.TradingSystem.database;

import org.TradingSystem.model.Account;
import org.TradingSystem.model.TradingAccount;

import java.util.List;


/*
Date: 4/22/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Abstract Data Access Object interface for CRUD on accounts
 */
public interface AccountDao<T extends Account> {

    T getAccount(int accountId, int customerId);

    T getPendingAccount(int accountId, int customerId);

    List<T> getAllActive(int customerId);

    List<T> getAllPending(int customerId);

    void update(T t);


    void delete(T t);

    void deleteFromPending(T t);

    void addPendingAccount(int customerId, String type);

    void addTradingAccount(TradingAccount tradingAccount);
}
