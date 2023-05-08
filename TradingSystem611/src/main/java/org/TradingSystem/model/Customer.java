package org.TradingSystem.model;


import org.TradingSystem.database.PeopleDao;

import java.util.List;

public class Customer extends Person {
    public Customer(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
    }

    public void deleteMessage(int messageId){
        MessageCenter.getInstance().deleteMessage(messageId);
    }

    public List<Message> getAllMessages(){
        return MessageCenter.getInstance().getMessagesInInbox(getID());
    }

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

    public List<TradingAccount> getAllAccounts(){
        return TradingAccount.getAllActive(getID());
    }
    }
