package org.TradingSystem.model;

public class AccountFactory {
    public Account createAccount(String accountType, int accountNumber){
        if(accountType.equalsIgnoreCase("TradingAccount")){
            return new TradingAccount(accountNumber);
        }else{
            throw new IllegalArgumentException("Invalid Account Type");
        }
    }
}
