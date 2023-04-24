package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeopleDao {

    private Connection connecton;

    public PeopleDao(){
        connecton = DatabaseConnection.getConnection();
    }

    public Customer getCustomer(int personId){
        return null;
    }

    public Manager getManager(int personId){
        return null;
    }

    public Customer login(String username, String password){
        return null;
    }

    public Manager managerLogin(String username, String password){
        return null;
    }

    public boolean addCustomer(Customer customer){
        return false;
    }

    public void updateCustomer(Customer customer){

    }

    public void updateManager(Manager manager){

    }

    public void changePassword(String ssn, String username, String password){

    }

    public void deleteCustomer(Customer customer){

    }

    public void deleteManager(Manager manager){

    }
}
