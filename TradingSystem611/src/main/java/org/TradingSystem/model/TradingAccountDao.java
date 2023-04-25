package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
Date: 4/22/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Data Access Object to perform CRUD on TradingAccounts across DB
 */
public class TradingAccountDao implements AccountDao<TradingAccount>{
    private Connection connection;
    public TradingAccountDao(){
        connection = DatabaseConnection.getConnection();
    }
    @Override
    public TradingAccount getAccount(int accountId, int customerId) {
        try{
            String sql = "SELECT * FROM activeAccounts WHERE accountNumber = ? "
                    + " AND customerId = ?"
                    + " AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,accountId);
            pstmt.setInt(2, customerId);
            pstmt.setString(3, "tradingAccount");

            ResultSet rs    = pstmt.executeQuery();
            if(rs.next()){
                return new TradingAccount(accountId, customerId, rs.getDouble("balance"), rs.getDouble("unrealizedPL"), rs.getDouble("realizedPL"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }



    @Override
    public TradingAccount getPendingAccount(int accountId, int customerId) {
        try{
            String sql = "SELECT * FROM pendingAccounts WHERE accountNumber = ? "
                    + " AND customerId = ?"
                    + " AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,accountId);
            pstmt.setInt(2, customerId);
            pstmt.setString(3, "tradingAccount");

            ResultSet rs    = pstmt.executeQuery();
            if(rs.next()){
                return new TradingAccount(accountId, customerId, 0, 0, 0);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<TradingAccount> getAllActive(int customerId) {
        try{
            String sql = "SELECT * FROM activeAccounts WHERE"
                    + " customerId = ?"
                    + " AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, "tradingAccount");

            ResultSet rs    = pstmt.executeQuery();

            ArrayList<TradingAccount> accounts = new ArrayList<TradingAccount>();
            while(rs.next()){
                accounts.add(new TradingAccount(rs.getInt("accountNumber"), rs.getInt("customerId"), rs.getDouble("balance"), rs.getDouble("unrealizedPL"), rs.getDouble("realizedPL")));
            }
            return accounts;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<TradingAccount> getAllPending(int customerId) {
        try{
            String sql = "SELECT * FROM pendingAccounts WHERE"
                    + " customerId = ?"
                    + " AND type = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, "tradingAccount");

            ResultSet rs    = pstmt.executeQuery();
            ArrayList<TradingAccount> accounts = new ArrayList<TradingAccount>();
            while(rs.next()){
                accounts.add(new TradingAccount(rs.getInt("accountNumber"), rs.getInt("customerId"), 0, 0, 0));
            }
            return accounts;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public void update(TradingAccount tradingAccount) {
        String sql = "UPDATE activeAccounts SET balance = ? , "
                + "unrealizedPL = ?,"
                + "realizedPL = ?"
                + "WHERE accountNumber = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1,tradingAccount.getBalance());
            pstmt.setDouble(2,tradingAccount.getUnrealizedProfitLoss());
            pstmt.setDouble(3, tradingAccount.getRealizedProfitLoss());
            pstmt.setInt(4, tradingAccount.getAccountNumber());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }

    }


    @Override
    public void delete(TradingAccount tradingAccount) {
        String sql = "DELETE FROM activeAccounts WHERE accountNumber = ?"
                + "AND customerId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,tradingAccount.getAccountNumber());
            pstmt.setDouble(2, tradingAccount.getPersonId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void deleteFromPending(TradingAccount tradingAccount) {
        String sql = "DELETE FROM pendingAccounts WHERE accountNumber = ?"
                + "AND customerId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,tradingAccount.getAccountNumber());
            pstmt.setDouble(2, tradingAccount.getPersonId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void addPendingAccount(int customerId, String type) {
        String sql = "INSERT INTO pendingAccounts (customerId, type)"
                + "VALUES (?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,customerId);
            pstmt.setString(2, type);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    //only the manager should use this to approve pending accounts, just takes the "pending" account and adds it to active table
    @Override
    public void addTradingAccount(TradingAccount tradingAccount) {
        String sql = "INSERT INTO activeAccounts (accountNumber, customerId, type, balance, unrealizedPL, realizedPL)"
                + "VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,tradingAccount.getAccountNumber());
            pstmt.setInt(2, tradingAccount.getPersonId());
            pstmt.setString(3,"tradingAccount");
            pstmt.setDouble(4,tradingAccount.getBalance());
            pstmt.setDouble(5,tradingAccount.getUnrealizedProfitLoss());
            pstmt.setDouble(6,tradingAccount.getRealizedProfitLoss());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
