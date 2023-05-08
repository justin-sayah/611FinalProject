package org.TradingSystem.model;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.database.TradingAccountDao;

import java.util.List;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Object representing Manager with all necessary behaviors and data
 */
public class Manager extends Person {

    public Manager(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
    }

    public Market getMarket(){
        return Market.getInstance();
    }

    /*
        adds a new pending account for a user into the system
     */
    public static boolean addNewPendingAccount(int customerId, String accountType){
        TradingAccountDao.getInstance().addPendingAccount(customerId, accountType);
        return true;
    }

    /*
        fetches a pending account if one exists
     */
    public TradingAccount getPendingAccount(int accountId, int customerId){
        return TradingAccountDao.getInstance().getPendingAccount(accountId, customerId);
    }

    /*
    takes a pending account and activates it to full trading status
     */
    public boolean activateAccount(TradingAccount account){
        TradingAccountDao.getInstance().deleteFromPending(account);
        TradingAccountDao.getInstance().addTradingAccount(account);
        return true;
    }

    /*
    Deletes the passed account from the system
     */
    public boolean deleteAccount(TradingAccount account){
        TradingAccountDao.getInstance().delete(account);
        return true;
    }

    /*
    returns a list of all active accounts for a given customer
     */
    public List<TradingAccount> getAllActiveAccounts(int customerId){
        return TradingAccountDao.getInstance().getAllActive(customerId);
    }

    /*
    returns the list of all customers in the system
     */
    public List<Customer> getAllCustomers(){
        return PeopleDao.getInstance().getAllCustomers();
    }


    /*
    fetches the Customer object corresponding to the customerId passed, if it exists
     */
    public static Customer getCustomer(int customerId){
        return PeopleDao.getInstance().getCustomer(customerId);
    }

    /*
    fetches the Manager corresponding to the personId, if it exists
     */
    public static Manager getManager(int personId){
        return PeopleDao.getInstance().getManager(personId);
    }

    /*
    wrapper method to send a message to a given Customer
     */
    public void sendMessage(Customer customer, String text){
        MessageCenter.getInstance().sendMessage(getID(), customer.getID(), text);
    }

    /*
    wrapper method to create/register a new Customer and save them in the database
     */
    public static boolean createCustomer(String firstName, String lastName, String username, String password, String dob, String ssn){
        return PeopleDao.getInstance().createCustomer(firstName, lastName, username, password, dob, ssn);
    }

    /*
    returns a Manager if the login credentials for that manager are correct
     */
    public static Manager managerLogin(String username, String password){
        return PeopleDao.getInstance().managerLogin(username, password);
    }

    /*
    wrapper method to push data changes for a Customer to the persistent DB
     */
    public static void updateCustomer(Customer customer){
        PeopleDao.getInstance().updateCustomer(customer);
    }

    /*
    wrapper method to push data changes for a Manager to the persistent DB
     */
    public static void updateManager(Manager manager){
        PeopleDao.getInstance().updateManager(manager);
    }

    /*
    pushes change to password for a Person if the ssn and username match existing data
     */
    public static void changePassword(String ssn, String username, String password){
        PeopleDao.getInstance().changePassword(ssn, username, password);
    }

    /*
    removes a Manager from the system permaneantly
     */
    public static void deleteManager(Manager manager){
        PeopleDao.getInstance().deleteManager(manager);
    }

    /*
    Method for the manager to block a customer's account from buying new stocks and from depositing money into that account
     */
    public static void blockAccount(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().blockAccount(tradingAccount);
    }

    /*
    Method for the manager to block a customer's account from buying new stocks and from depositing money into that account
     */
    public static void blockAccount(int accountId){
        TradingAccountDao.getInstance().blockAccount(accountId);
    }

    /*
    Method for the manager to unblock a customer's account from buying new stocks and from depositing money into that account
     */
    public static void unblockAccount(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().unblockAccount(tradingAccount);
    }

    /*
    Method for the manager to unblock a customer's account from buying new stocks and from depositing money into that account
     */
    public static void unblockAccount(int accountId){
        TradingAccountDao.getInstance().unblockAccount(accountId);
    }

        @Override
    public String toString() {
        return "Manager! PersonId: " + getID() + "     Name: " + getFirstName() + " " + getLastName();
    }

}
