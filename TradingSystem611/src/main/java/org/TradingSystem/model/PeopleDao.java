package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
Date: 4/23/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Data Access Object to perform CRUD on People (Manager/Customer)
*/

public class PeopleDao {

    private Connection connection;

    public PeopleDao(){
        connection = DatabaseConnection.getConnection();
    }

    public Customer getCustomer(int personId){
        try{
            String sql = "SELECT * FROM people WHERE personId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,personId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Customer(personId, rs.getString("firstName"), rs.getString("lastName"),rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM people";

            PreparedStatement pstmt = connection.prepareStatement(sql);


            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                list.add(new Customer(rs.getInt("personId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn")));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return list;
    }

    //gets manager from db if it exists
    public Manager getManager(int personId){
        try{
            String sql = "SELECT * FROM people WHERE personId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,personId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Manager(personId, rs.getString("firstName"), rs.getString("lastName"),rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //returns Customer object if login was successful, null otherwise
    public Customer login(String username, String password){
        try{
            String sql = "SELECT * FROM people WHERE username = ?" +
                    "AND password = ?" +
                    "AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setString(3,"customer");

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Customer(rs.getInt("personId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("username"), rs.getString("password"),rs.getString("dob"),rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public Manager managerLogin(String username, String password){
        try{
            String sql = "SELECT * FROM people WHERE" +
                    "username = ?" +
                    "AND password = ?" +
                    "AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setString(3,"manager");

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Manager(rs.getInt("personId"), rs.getString("firstName"), rs.getString("lastName"),rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean addCustomer(Customer customer){
        String sql = "INSERT INTO people (personId, firstName, lastName, type, username, password, dob, ssn)"
                + "VALUES (?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customer.getID());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, "customer");
            pstmt.setString(5,customer.getUsername());
            pstmt.setString(6,customer.getPassword());
            pstmt.setString(7, customer.getDob());
            pstmt.setString(8, customer.getSsn());

            pstmt.executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean createCustomer(String firstName, String lastName, String username, String password, String dob, String ssn){
        String sql = "INSERT INTO people (firstName, lastName, type, username, password, dob, ssn)"
                + "VALUES (?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, "customer");
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.setString(6, dob);
            pstmt.setString(7, ssn);

            pstmt.executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public void updateCustomer(Customer customer){
        String sql = "UPDATE people SET firstName = ? , "
                + "lastName = ?"
                + "username = ?,"
                + "password = ?," +
                "dob = ?," +
                "ssn = ?"
                + "WHERE personId = ? AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getUsername());
            pstmt.setString(4, customer.getPassword());
            pstmt.setString(5, customer.getDob());
            pstmt.setString(6,customer.getSSN());
            pstmt.setInt(7, customer.getID());
            pstmt.setString(8, "customer");

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void updateManager(Manager manager){
        String sql = "UPDATE people SET firstName = ? , "
                + "lastName = ?,"
                + "username = ?,"
                + "password = ?," +
                "dob = ?," +
                "ssn = ?"
                + "WHERE personId = ? AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, manager.getFirstName());
            pstmt.setString(2, manager.getLastName());
            pstmt.setString(3, manager.getUsername());
            pstmt.setString(4, manager.getPassword());
            pstmt.setString(5, manager.getDob());
            pstmt.setString(6,manager.getSSN());
            pstmt.setInt(7, manager.getID());
            pstmt.setString(8, "customer");

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void changePassword(String ssn, String username, String password){
        String sql = "UPDATE people SET password = ?"
                + "WHERE ssn = ? AND type = ? AND username = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, ssn);
            pstmt.setString(3, "customer");
            pstmt.setString(4, username);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteCustomer(Customer customer){
        String sql = "DELETE FROM people WHERE personId = ?" +
                "AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customer.getID());
            pstmt.setString(2, "customer");

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteManager(Manager manager){
        String sql = "DELETE FROM people WHERE personId = ?" +
                "AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, manager.getID());
            pstmt.setString(2, "manager");

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
