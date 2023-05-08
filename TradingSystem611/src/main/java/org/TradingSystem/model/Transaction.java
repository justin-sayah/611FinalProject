package org.TradingSystem.model;

import org.TradingSystem.database.TransactionDao;

import java.util.List;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Object representing data and behaviors of TradingAccounts. implements Account
 */
public class Transaction {
    private int transactionId;
    private int accountId;
    private int stockId;
    private int quantity;
    private double price;
    private String timestamp;
    private String action;

    public Transaction(int transactionId, int accountId, int stockId, int quantity, double price, String timestamp, String action){
        setTransactionId(transactionId);
        setAccountId(accountId);
        setStockId(stockId);
        setQuantity(quantity);
        setPrice(price);
        setTimestamp(timestamp);
        setAction(action);
    }

    //pushes any changes to this specific Transaction to persistent storage
    public void updateTransaction(){
        TransactionDao tDao = TransactionDao.getInstance();
        tDao.updateTransaction(this);
    }

    public double getPrice() {
        return price;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getStockId() {
        return stockId;
    }

    public String getAction() {
        return action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public static Transaction getTransaction(int transactionId){
        return TransactionDao.getInstance().getTransaction(transactionId);
    }

    public static List<Transaction> getAllTransactionsByAccount(int accountId){
        return TransactionDao.getInstance().getAllTransactionsByAccount(accountId);
    }

    public static List<Transaction> getAllTransactionsByStock(int stockId){
        return TransactionDao.getInstance().getAllTransactionsByStock(stockId);
    }

    public static void addTransaction(int accountId, int stockId, int quantity, double price, String action){
        TransactionDao.getInstance().addTransaction(accountId, stockId, quantity, price, action);
    }

    public static void addTransaction(Transaction transaction){
        TransactionDao.getInstance().addTransaction(transaction);

    }

    public static void deleteTransaction(Transaction transaction){
        TransactionDao.getInstance().deleteTransaction(transaction);
    }

    public static void deleteTransaction(int transactionId){
        TransactionDao.getInstance().deleteTransaction(transactionId);
    }

    public static void updateTransaction(Transaction transaction){
        TransactionDao.getInstance().updateTransaction(transaction);
    }

    @Override
    public String toString() {
        return "Account Id: " + getAccountId() + "   Action: " + getAction() + "  StockId " + getStockId() + "  Quantity: " + getQuantity() + "  Price: " + getPrice() ;
    }
}
