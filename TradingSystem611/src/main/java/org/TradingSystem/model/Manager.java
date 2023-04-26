package org.TradingSystem.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Person {

    private ArrayList<TradingAccount> accounts;
    private ArrayList<Customer> customers;
    private TradingAccountDao dao;
    public Manager(int id, String firstName, String lastName, String username, String password, String dob, String ssn){
        super(id, firstName, lastName, username, password, dob, ssn);
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.dao = new TradingAccountDao();
    }

    public void createNewAccount(String accountType, int accountNumber){
        if(!accountType.equals("TradingAccount")){
            return;
        }
        TradingAccount account = new TradingAccount(accountNumber);
        dao.addTradingAccount(account);
    }

    public boolean activateAccount(TradingAccount account){
        dao.deleteFromPending(account);
        dao.addTradingAccount(account);
        return true;
    }

    public boolean pendAccount(TradingAccount account){
        dao.delete(account);
        dao.addPendingAccount(account.getPersonId(), "TradingAccount");
        return true;
    }

    public boolean deleteAccount(TradingAccount account){
        dao.delete(account);
        return true;
    }

    public TradingAccount getPendingAccount(int accountId, int customerId){
        return dao.getPendingAccount(accountId, customerId);
    }

    public  List<TradingAccount> getAllActive(int customerId){
        return dao.getAllActive(customerId);
    }

    public Customer getCustomer(int customerId){
        return new PeopleDao().getCustomer(customerId);
    }

    public List<Customer> getAllCustomers(){
        return new PeopleDao().getAllCustomers();
    }


//    public void createNewAccount(String accountType, int accountNumber){
//        if(!accountType.equals("TradingAccount")){
//            return;
//        }
//        TradingAccount account = new TradingAccount(accountNumber);
//        accounts.add(account);
//    }

//    public boolean activateAccount(TradingAccount account){
//        if(account.getActive() == false){
//            account.setActive(true);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean deactivateAccount(TradingAccount account){
//        if(account.getActive() == true){
//            account.setActive(false);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean deleteAccount(TradingAccount account){
//        if(accounts.contains(account)){
//            accounts.remove(account);
//            return true;
//        }
//        return false;
//    }
//
//    public ArrayList<TradingAccount> getPendingAccounts(){
//        ArrayList<TradingAccount> list = new ArrayList<TradingAccount>();
//        for(int i = 0; i < accounts.size(); i++){
//            if(accounts.get(i).getActive() == false){
//                list.add(accounts.get(i));
//            }
//        }
//        return list;
//    }
//
//    public ArrayList<TradingAccount> getActiveAccounts(){
//        ArrayList<TradingAccount> list = new ArrayList<TradingAccount>();
//        for(int i = 0; i < accounts.size(); i++){
//            if(accounts.get(i).getActive() == true){
//                list.add(accounts.get(i));
//            }
//        }
//        return list;
//    }

    /*public Customer getCustomer(int id){
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getID() == id){
                return customers.get(i);
            }
        }
        return null;
    }*/

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public void sendMessage(Customer customer, int messageID, String text){
        customer.receiveMessage(new Message(messageID, this.getID(), customer.getID(), text));
    }

    public Manager getManager(int id){
        return new PeopleDao().getManager(id);
    }

    @Override
    public String toString() {
        return "Manager! PersonId: " + getID() + "     Name: " + getFirstName() + " " + getLastName();
    }
}
