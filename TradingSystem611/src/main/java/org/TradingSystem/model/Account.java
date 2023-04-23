package org.TradingSystem.model;

public interface Account {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
    int getAccountNumber();
}
