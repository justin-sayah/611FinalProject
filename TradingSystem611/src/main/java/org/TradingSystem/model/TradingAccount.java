package org.TradingSystem.model;

public class TradingAccount implements Account{
    protected int accountNumber;
    protected int customerId;
    protected double balance;
    protected boolean active;

    protected double unrealizedProfitLoss;
    protected double realizedProfitLoss;

//Trading Account constructor with two variables: Account Number and Balance
    public TradingAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        balance = 0.0;
        unrealizedProfitLoss = 0.0;
        realizedProfitLoss = 0.0;
        active = false;
    }

    public TradingAccount(int accountNumber, int customerId, double balance, double unrealizedProfitLoss, double realizedProfitLoss) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.realizedProfitLoss = realizedProfitLoss;
        this.active = false;
    }

    //update the balance by adding the amount of deposit
    @Override
    public void deposit(double amount) {
        if(this.active == false){
            return;
        }
        balance += amount;
    }

    //update the balance by subtracting the amount of withdraw
    @Override
    public void withdraw(double amount) {
        if(this.active == false){
            return;
        }
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

    public double getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    @Override
    public int getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public boolean getActive(){
        return active;
    }

    public void setActive(boolean x){
        this.active = x;
    }

    //FOR TESTING PURPOSES
    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "    Balance: " + balance;
    }
}
