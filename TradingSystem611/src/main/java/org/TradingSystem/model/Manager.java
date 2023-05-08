package org.TradingSystem.model;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.database.TradingAccountDao;

import java.util.List;

public class Manager extends Person {

    public Manager(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
    }

    public Market getMarket(){
        return Market.getInstance();
    }

    public static boolean addNewPendingAccount(int customerId, String accountType){
        TradingAccountDao.getInstance().addPendingAccount(customerId, accountType);
        return true;
    }

    public TradingAccount getPendingAccount(int accountId, int customerId){
        return TradingAccountDao.getInstance().getPendingAccount(accountId, customerId);
    }

    public boolean activateAccount(TradingAccount account){
        TradingAccountDao.getInstance().deleteFromPending(account);
        TradingAccountDao.getInstance().addTradingAccount(account);
        return true;
    }

    public boolean deleteAccount(TradingAccount account){
        TradingAccountDao.getInstance().delete(account);
        return true;
    }

    public List<TradingAccount> getAllActiveAccounts(int customerId){
        return TradingAccountDao.getInstance().getAllActive(customerId);
    }

    public List<Customer> getAllCustomers(){
        return PeopleDao.getInstance().getAllCustomers();
    }


    public static Customer getCustomer(int customerId){
        return PeopleDao.getInstance().getCustomer(customerId);
    }
    public static Manager getManager(int personId){
        return PeopleDao.getInstance().getManager(personId);
    }

    public void sendMessage(Customer customer, String text){
        MessageCenter.getInstance().sendMessage(getID(), customer.getID(), text);
    }

    public static boolean createCustomer(String firstName, String lastName, String username, String password, String dob, String ssn){
        return PeopleDao.getInstance().createCustomer(firstName, lastName, username, password, dob, ssn);
    }

    public static Manager managerLogin(String username, String password){
        return PeopleDao.getInstance().managerLogin(username, password);
    }

    public static void updateCustomer(Customer customer){
        PeopleDao.getInstance().updateCustomer(customer);
    }

    public static void updateManager(Manager manager){
        PeopleDao.getInstance().updateManager(manager);
    }

    public static void changePassword(String ssn, String username, String password){
        PeopleDao.getInstance().changePassword(ssn, username, password);
    }
    public static void deleteManager(Manager manager){
        PeopleDao.getInstance().deleteManager(manager);
    }

    public static void blockAccount(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().blockAccount(tradingAccount);
    }

    public static void blockAccount(int accountId){
        TradingAccountDao.getInstance().blockAccount(accountId);
    }

    public static void unblockAccount(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().unblockAccount(tradingAccount);
    }

    public static void unblockAccount(int accountId){
        TradingAccountDao.getInstance().unblockAccount(accountId);
    }

        @Override
    public String toString() {
        return "Manager! PersonId: " + getID() + "     Name: " + getFirstName() + " " + getLastName();
    }

}
