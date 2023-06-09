package org.TradingSystem.model;

import org.TradingSystem.database.TradingAccountDao;

import java.util.List;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Object representing data and behaviors of TradingAccounts. implements Account
 */
public class TradingAccount  implements Account{
    protected int accountNumber;
    protected int personId;
    protected double balance;
    protected double unrealizedProfitLoss;
    protected double realizedProfitLoss;

    //Trading Account constructor with two variables: Account Number and Balance
    public TradingAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        balance = 0.0;
        unrealizedProfitLoss = 0.0;
        realizedProfitLoss = 0.0;
    }

    public TradingAccount(int accountNumber, int personId, double balance, double unrealizedProfitLoss, double realizedProfitLoss) {
        this.accountNumber = accountNumber;
        this.personId = personId;
        this.balance = balance;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.realizedProfitLoss = realizedProfitLoss;
    }

    //update the balance by adding the amount of deposit
    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    //update the balance by subtracting the amount of withdraw
    @Override
    public void withdraw(double amount) {
        if(balance - amount <0){
            System.out.println("You can only withdraw " + balance);
            balance = 0;
        }else{
            balance -= amount;
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public double getRealizedProfitLoss() {
        return realizedProfitLoss;
    }

    public void setRealizedProfitLoss(double realizedProfitLoss) {
        this.realizedProfitLoss = realizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(double unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public double getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    @Override
    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPersonId() {
        return personId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void refresh(){
        TradingAccountDao.getInstance().refreshTradingAccount(this);
    }

    public List<Position> getAllPositions(){
        return Position.getAllPositions(accountNumber);
    }


    //BEGINNING OF ALL STATIC WRAPPER METHODS FOR PERSISTENT STORAGE FETCHING AND SAVING

    public static TradingAccount getAccount(int accountId){
        return TradingAccountDao.getInstance().getAccount(accountId);
    }

    //fetches an account without pushing it back to the DB
    public static TradingAccount getAccountNoRefresh(int accountId){
        return TradingAccountDao.getInstance().getAccountNoRefresh(accountId);
    }

    public static TradingAccount getPendingAccount(int accountId, int customerId){
        return TradingAccountDao.getInstance().getPendingAccount(accountId, customerId);
    }

    public static List<TradingAccount> getAllActive(int customerId){
        return TradingAccountDao.getInstance().getAllActive(customerId);
    }

    public static List<TradingAccount> getAllActive(){
        return TradingAccountDao.getInstance().getAllActive();
    }

    public static List<TradingAccount> getAllPending(){
        return TradingAccountDao.getInstance().getAllPending();
    }

    public static List<TradingAccount> getAllPending(int customerId){
        return TradingAccountDao.getInstance().getAllPending(customerId);
    }

    //pushes changes to this TradingAccount to persistent storage
    public static void update(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().update(tradingAccount);
    }

    public static void delete(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().delete(tradingAccount);
    }

    public static void deleteFromPending(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().deleteFromPending(tradingAccount);
    }

    public static void addPendingAccount(int customerId, String type){
        TradingAccountDao.getInstance().addPendingAccount(customerId, type);
    }

    public static void addTradingAccount(TradingAccount tradingAccount){
        TradingAccountDao.getInstance().addTradingAccount(tradingAccount);
    }

    //gets all blocked accounts
    public static List<TradingAccount> getAllBlocked(){
        return TradingAccountDao.getInstance().getAllBlocked();
    }

    //gets all blocked accounts for a specific user
    public static List<TradingAccount> getAllBlocked(int customerId){
        return TradingAccountDao.getInstance().getAllBlocked(customerId);
    }

    //checks if an account is blocked or not
    public static boolean isBlocked(int accountId){
        return TradingAccountDao.getInstance().isBlocked(accountId);
    }

    //checks if an account is blocked or not
    public static boolean isBlocked(TradingAccount tradingAccount){
        return TradingAccountDao.getInstance().isBlocked(tradingAccount);
    }

    public String getType(){
        return "TradingAccount";
    }



    //FOR TESTING PURPOSES
    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "    Balance: " + balance;
    }
}
