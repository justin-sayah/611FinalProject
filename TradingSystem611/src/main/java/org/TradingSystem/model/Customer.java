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
