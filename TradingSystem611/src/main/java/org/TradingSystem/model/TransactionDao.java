package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private Connection connection;

    public TransactionDao(){
        connection = DatabaseConnection.getConnection();
    }

    public Transaction getTransaction(int transactionId){
        return null;
    }
    public List<Transaction> getAllTransactionsByAccount(int accountId){
        return null;
    }
    public List<Transaction> getAllTransactions(int accountId, int securityId){
        return null;
    }
    public List<Transaction> getAllTransactionsByStock(int stockId){
        return null;
    }
//    getAllTransactions(Timestamp1, Timestamp2) ????
    public void addTransaction(int accountId, int stockId, int quantity, double price, String timestamp, String action){

    }
    public void addTransaction(Transaction transaction){

    }
    public void deleteTransaction(Transaction transaction){

    }

    public void deleteTransaction(int transactionId){

    }
    public void updateTransaction(Transaction transaction){
        
    }
}
