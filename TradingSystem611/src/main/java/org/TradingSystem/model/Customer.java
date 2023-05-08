package org.TradingSystem.model;


import org.TradingSystem.database.PeopleDao;

import java.util.List;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Object representing Customer with all necessary behaviors and data
 */
public class Customer extends Person {
    public Customer(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
    }

    /*
    Uses DB interface to delete a message from DB by messageId
     */
    public void deleteMessage(int messageId){
        MessageCenter.getInstance().deleteMessage(messageId);
    }

    /*
    Returns all messages in the inbox of this Customer
     */
    public List<Message> getAllMessages(){
        return MessageCenter.getInstance().getMessagesInInbox(getID());
    }

    /*
    Returns list of all active trading accounts that this customer owns
     */
    public List<TradingAccount> getAllAccounts(){
        return TradingAccount.getAllActive(getID());
    }


    //BEGINNING OF STATIC WRAPPER METHODS FOR DATABASE INTERACTION

    public static Customer getCustomer(int personId){
        PeopleDao pDao = PeopleDao.getInstance();
        return pDao.getCustomer(personId);
    }

    public static Customer customerLogin(String username, String password){
        return PeopleDao.getInstance().login(username, password);
    }

    public static void updateCustomer(Customer customer){
        PeopleDao.getInstance().updateCustomer(customer);
    }

    public static void changePassword(String ssn, String username, String password){
        PeopleDao.getInstance().changePassword(ssn, username, password);
    }

    public static void deleteCustomer(Customer customer){
        PeopleDao.getInstance().deleteCustomer(customer);
    }


    }
