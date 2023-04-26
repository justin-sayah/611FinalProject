package org.TradingSystem.model;

import java.util.ArrayList;

public class Customer extends Person {
    ArrayList<TradingAccount> accounts;
    ArrayList<Message> messages;
    public Customer(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
        accounts = new ArrayList<TradingAccount>();
        messages = new ArrayList<Message>();
    }
    //MANAGER should see all new accounts and approve them
//    public void createNewAccount(String accountType, int accountNumber){
//        AccountFactory factory = new AccountFactory();
//        Account account = factory.createAccount(accountType, accountNumber);
//        if(account instanceof TradingAccount){
//            accounts.add((TradingAccount)account);
//        }
//    }

    public void receiveMessage(Message message){
        messages.add(message);
    }

    public ArrayList<Message> getAllMessages(){
        return messages;
    }
}
