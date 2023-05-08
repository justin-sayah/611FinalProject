package org.TradingSystem.database;

import org.TradingSystem.model.Customer;
import org.TradingSystem.model.Manager;
import org.TradingSystem.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public static PeopleDao pDao;

    private PeopleDao(){
        connection = DatabaseConnection.getConnection();
    }

    public static PeopleDao getInstance() {
        if(pDao == null){
            pDao = new PeopleDao();
        }
        return  pDao;
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

    public static class StockDao {
        private Connection connection;
        public static StockDao stockDao;

        private StockDao(){
            connection = DatabaseConnection.getConnection();
        }

        public static StockDao getInstance() {
            if(stockDao == null){
                stockDao = new StockDao();
            }
            return stockDao;
        }

        public void refreshStock(Stock stock){
            Stock fresh = getStock(stock.getSecurityId());
            stock.changePrice(fresh.getPrice());
        }

        //gets the stock, whether it is blocked or unblocked
        public Stock getStock(int stockId){
            try{
                String sql = "SELECT * FROM stocks WHERE stockId = ? ";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, stockId);

                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    return new Stock(stockId, rs.getDouble("price"), rs.getString("name"), rs.getString("ticker") );
                } else{
                    String sql2 = "SELECT * FROM blockedStocks WHERE stockId = ? ";

                    PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                    pstmt2.setInt(1, stockId);

                    ResultSet rs2 = pstmt2.executeQuery();
                    if(rs2.next()){
                        return new Stock(stockId, rs2.getDouble("price"), rs2.getString("name"), rs2.getString("ticker") );
                    }
                }
            }catch (Exception e){
                System.out.println(e.toString());
            }
            return null;
        }

        //Returns all unblocked and blocked stocks
        public List<Stock> getAllStocks(){
            List<Stock> allUnblocked = getAllUnblockedStocks();
            List<Stock> allBlocked = getAllBlockedStocks();

            allUnblocked.addAll(allBlocked);
            return allUnblocked;
        }

        //returns only unblocked stocks
        public List<Stock> getAllUnblockedStocks(){
            try{
                String sql = "SELECT * FROM stocks";

                PreparedStatement pstmt = connection.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();

                ArrayList<Stock> stocks = new ArrayList<Stock>();
                while(rs.next()){
                    stocks.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
                }
                return stocks;
            }catch (Exception e){
                System.out.println(e.toString());
            }
            return null;
        }

        //returns only blocked stocks
        public List<Stock> getAllBlockedStocks(){
            try{
                String sql = "SELECT * FROM blockedStocks";

                PreparedStatement pstmt = connection.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();

                ArrayList<Stock> stocks = new ArrayList<Stock>();
                while(rs.next()){
                    stocks.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
                }
                return stocks;
            }catch (Exception e){
                System.out.println(e.toString());
            }
            return null;
        }

        //adds a stock to unblocked list
        public void addStock(String name, double price, String ticker){
            String sql = "INSERT INTO stocks (name, price, ticker)" +
                    "VALUES (?,?,?)";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setString(3, ticker);

                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        public void updateStockPrice(Stock stock, double price){
            String sql = "UPDATE stocks SET price = ? "
                    + "WHERE stockId = ?";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, price+"");
                pstmt.setDouble(2, stock.getSecurityId());


                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        //adds a stock to unblocked list
        public void addStock(Stock stock){
            String sql = "INSERT INTO stocks (stockId, name, " +
                    "price, ticker)" +
                    "VALUES (?,?,?,?)";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1,stock.getSecurityId());
                pstmt.setString(2, stock.getName());
                pstmt.setDouble(3, stock.getPrice());
                pstmt.setString(4, stock.getTicker());

                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        //removes a stock from unblocked list and moves it to blocked list
        public void blockStock(Stock stock){

            //first remove from stocks table
            deleteFromUnblocked(stock.getSecurityId());

            //then add to blockedStock table
            String sql = "INSERT INTO blockedStocks (stockId, name, " +
                    "price, ticker)" +
                    "VALUES (?,?,?,?)";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1,stock.getSecurityId());
                pstmt.setString(2, stock.getName());
                pstmt.setDouble(3, stock.getPrice());
                pstmt.setString(4, stock.getTicker());

                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        public void blockStock(int stockId){
            Stock toBlock = getStock(stockId);
            blockStock(toBlock);
        }

        //removes a stock from blocked list and moves it back to unblocked list
        public void unblockStock(Stock stock){

            System.out.println(stock);

            //delete from blocked table
            deleteFromBlocked(stock.getSecurityId());

            //add to unblocked stocks
            addStock(stock);

        }

        public void unblockStock(int stockId){
            //get the stock
            Stock toUnblock = getStock(stockId);

            unblockStock(toUnblock);
        }

        //removes a stock from both blocked and unblocked list
        public void deleteStock(int stockId){
            //TODO: Sell positions for all people that own this stock
            deleteFromUnblocked(stockId);
            deleteFromBlocked(stockId);
        }

        //removes stock from unblocked and blocked list
        public void deleteStock(Stock stock){
            deleteStock(stock.getSecurityId());
        }

        //deletes a stock from unblocked list
        private void deleteFromUnblocked(int stockId){
            String sql = "DELETE FROM stocks WHERE stockId = ?";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, stockId);

                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        public boolean isBlocked(int stockId){
            try{
                String sql = "SELECT * FROM stocks WHERE stockId = ? ";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, stockId);

                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    return false;
                } else{
                    return true;
                }
            }catch (Exception e){
                System.out.println(e.toString());
            }
            return false;
        }

        //deletes a stock from blocked list
        private void deleteFromBlocked(int stockId){
            String sql = "DELETE FROM blockedStocks WHERE stockId = ?";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, stockId);

                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        public List<Stock> getStocks(String tickerQuery){
            try{
                String sql = "SELECT * FROM stocks WHERE"
                        + " ticker LIKE ?";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, tickerQuery + "%");

                ResultSet rs = pstmt.executeQuery();

                ArrayList<Stock> accounts = new ArrayList<Stock>();
                while(rs.next()){
                    accounts.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
                }
                return accounts;
            }catch (Exception e){
                System.out.println(e.toString());
            }
            return null;
        }

        //updates stock regardless of if it is in blocked or unblocked state
        public void updateStock(Stock stock){
            updateUnblockedStock(stock);
            updateBlockedStock(stock);
        }

        private void updateUnblockedStock(Stock stock){
            String sql = "UPDATE stocks SET ticker = ? , "
                    + "price = ?, name = ?"
                    + "WHERE stockId = ?";
            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, stock.getTicker());
                pstmt.setDouble(2, stock.getPrice());
                pstmt.setString(3, stock.getName());
                pstmt.setInt(4, stock.getSecurityId());


                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }

        private void updateBlockedStock(Stock stock){
            String sql = "UPDATE blockedStocks SET ticker = ? , "
                    + "price = ?, name = ?"
                    + "WHERE stockId = ?";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, stock.getTicker());
                pstmt.setDouble(2, stock.getPrice());
                pstmt.setString(3, stock.getName());
                pstmt.setInt(4, stock.getSecurityId());


                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
