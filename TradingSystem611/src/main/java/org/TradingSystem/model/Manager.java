package org.TradingSystem.model;

import java.util.ArrayList;

public class Manager extends Person {

    private int profit;
    private ArrayList<TradingAccount> accounts;
    private ArrayList<Customer> customers;
    public Manager(int id, String username, String password, String ssn){
        super(id, username, password, ssn);
        this.profit = 0;
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public int getProfit(){
        return profit;
    }

    public void createNewAccount(String accountType, int accountNumber){
        if(!accountType.equals("TradingAccount")){
            return;
        }
        TradingAccount account = new TradingAccount(accountNumber);
        accounts.add(account);
    }

    public boolean activateAccount(TradingAccount account){
        if(account.getActive() == false){
            account.setActive(true);
            return true;
        }
        return false;
    }

    public boolean deactivateAccount(TradingAccount account){
        if(account.getActive() == true){
            account.setActive(false);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(TradingAccount account){
        if(accounts.contains(account)){
            accounts.remove(account);
            return true;
        }
        return false;
    }

    public ArrayList<TradingAccount> getPendingAccounts(){
        ArrayList<TradingAccount> list = new ArrayList<TradingAccount>();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getActive() == false){
                list.add(accounts.get(i));
            }
        }
        return list;
    }

    public ArrayList<TradingAccount> getActiveAccounts(){
        ArrayList<TradingAccount> list = new ArrayList<TradingAccount>();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getActive() == true){
                list.add(accounts.get(i));
            }
        }
        return list;
    }

    public Customer getCustomer(int id){
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getID() == id){
                return customers.get(i);
            }
        }
        return null;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public boolean get10KProfit(){
        if(profit >= 10000){
            return true;
        }
        return false;
    }

    public void sendMessage(Customer customer, Message message){
        customer.receiveMessage(message);
    }
}
