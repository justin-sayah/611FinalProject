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
                return new Customer(personId, rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //gets manager from db if it exists
    public Manager getManager(int personId){
        try{
            String sql = "SELECT * FROM people WHERE personId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,personId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Manager(personId, rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
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
                return new Customer(rs.getInt("personId"), rs.getString("name"), rs.getString("username"), rs.getString("password"),rs.getString("dob"),rs.getString("ssn"));
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
                return new Manager(rs.getInt("personId"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("dob"), rs.getString("ssn"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean addCustomer(Customer customer){
        String sql = "INSERT INTO people (personId, name, type, username, password, dob, ssn)"
                + "VALUES (?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customer.getID());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, "customer");
            pstmt.setString(4,customer.getUsername());
            pstmt.setString(5,customer.getPassword());
            pstmt.setString(6, customer.getDob());
            pstmt.setString(7, customer.getSsn());

            pstmt.executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean createCustomer(String name, String username, String password, String dob, String ssn){
        String sql = "INSERT INTO people (name, type, username, password, dob, ssn)"
                + "VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, "customer");
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, dob);
            pstmt.setString(6, ssn);

            pstmt.executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public void updateCustomer(Customer customer){
        String sql = "UPDATE people SET name = ? , "
                + "username = ?,"
                + "password = ?," +
                "dob = ?," +
                "ssn = ?"
                + "WHERE personId = ? AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getUsername());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getDob());
            pstmt.setString(5,customer.getSSN());
            pstmt.setInt(6, customer.getID());
            pstmt.setString(7, "customer");

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void updateManager(Manager manager){
        String sql = "UPDATE people SET name = ? , "
                + "username = ?,"
                + "password = ?," +
                "dob = ?," +
                "ssn = ?"
                + "WHERE personId = ? AND type = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, manager.getName());
            pstmt.setString(2, manager.getUsername());
            pstmt.setString(3, manager.getPassword());
            pstmt.setString(4, manager.getDob());
            pstmt.setString(5,manager.getSSN());
            pstmt.setInt(6, manager.getID());
            pstmt.setString(7, "customer");

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
