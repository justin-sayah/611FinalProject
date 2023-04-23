package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
                return new TradingAccount(accountId, customerId, rs.getDouble("balance"), rs.getDouble("unrealizedPL"), rs.getDouble("realizedPL"));
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
    public void update(TradingAccount tradingAccount) {
        String sql = "UPDATE activeAccounts SET balance = ? , "
                + "unrealizedLoss = ?,"
                + "realizedLoss = ?"
                + "WHERE accountId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1,tradingAccount.getUnrealizedProfitLoss());
            pstmt.setDouble(2, tradingAccount.getRealizedProfitLoss());
            pstmt.setInt(3, tradingAccount.getAccountNumber());

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
            pstmt.setDouble(2, tradingAccount.getCustomerId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void addPendingAccount(int customerId, String type) {
        
    }

    @Override
    public void addTradingAccount(int accountId, int customerId, String type) {

    }
}
