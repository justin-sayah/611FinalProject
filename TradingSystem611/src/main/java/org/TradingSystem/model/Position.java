package org.TradingSystem.model;

import org.TradingSystem.database.PositionDao;
import org.TradingSystem.database.StockDao;

import java.util.List;


/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Object representing data and behaviors of Positions
 */
public class    Position {
    protected int accountID;
    protected int quantity;
    protected double currentPrice;
    protected double avgBuyPrice;
    protected double realizedProfitLoss;
    protected double unrealizedProfitLoss;
    protected int securityId;
    protected int quantitySold;

    public Position(int accountID, int securityId, int quantity, int quantitySold, double currentPrice, double avgBuyPrice, double realizedProfitLoss, double unrealizedProfitLoss) {
        this.accountID = accountID;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.avgBuyPrice = avgBuyPrice;
        this.realizedProfitLoss = realizedProfitLoss;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.securityId = securityId;
        this.quantitySold = quantitySold;

        //make sure calculations on PL are up-to-date
        calculateUnrealizedPl();
        refresh();
    }

    //attempts to sell quantityToSell, updates PL and DB, and if quantity is 0, closes position in DB
    //recalculates PL for both Position and Account and pushes changes as well
    public void sell(int quantityToSell){
        if(quantityToSell <= 0){
            System.out.println("Can't sell 0 or less shares.");
        }
        else if(quantityToSell > quantity){
            System.out.println("Can't sell more than you have.");
        }
        else{
            quantity -= quantityToSell;
            quantitySold += quantityToSell;

            double net = quantityToSell * currentPrice;
            TradingAccount account = TradingAccount.getAccount(accountID);

            //add amount into balance
            account.deposit(net);

            //push account update
            TradingAccount.update(account);

            //recalculate local pl
            calculateRealizedPl(quantityToSell);
            calculateUnrealizedPl();

            //push position update
            refresh(this);
            Transaction.addTransaction(accountID, securityId, quantityToSell, currentPrice, "sell");
            if(quantity == 0){
                deletePosition(this);
            }
        }
    }

    //wrapper method for the static buy method
    public void buy(int stockId, int quantity){
        Position.buy(accountID, stockId, quantity);
    }

    //attempts to buy quantity units of stockId stock if it can be afforded.
    //will create a new Position or add to a current Position as necessary
    public static void buy(int accountId, int stockId, int quantity){
        StockDao sDao = StockDao.getInstance();
        double stockPrice = sDao.getStock(stockId).getPrice();
        double price = stockPrice * quantity;
        //check if this can be afforded
        TradingAccount account = TradingAccount.getAccount(accountId);
        if(account.getBalance() < price){
            return;
        }

        //attempt to fetch position, if it is new, create a new Position
        //if it not a new position, add to position
        Position p1 = getPosition(accountId, stockId);
        if(p1 == null){
            p1 = new Position(accountId, stockId, quantity, 0, stockPrice, stockPrice,0, 0);
            PositionDao pdao = PositionDao.getInstance();
            pdao.createPosition(p1);
        }

        //subtract cost of the purchase
        account.withdraw(price);
        TradingAccount.update(account);

        //create transaction
        Transaction.addTransaction(accountId, stockId, quantity, price, "buy");
        refresh(p1);
    }

    //adds quantityToAdd shares at the current sellPrice/buyPrice
    //creates a transaction to cement this purchase
    private void addToPosition(int quantityToAdd){
        //how much this purchase cost
        double newPurchaseCost = quantityToAdd* getCurrentPrice();

        //check if this can be afforded
        TradingAccount account = TradingAccount.getAccount(accountID);
        if(account.getBalance() < newPurchaseCost){
            return;
        }

        avgBuyPrice = ((quantity * avgBuyPrice)+(quantityToAdd * getCurrentPrice())) / (quantity + quantityToAdd);
        quantity += quantityToAdd;

        calculateUnrealizedPl();

        refresh();
    }

    //does a recalculation of unrealizedPL based on current stock prices and pushes changes to DB
    //also updates Account for this Customer
    private void calculateUnrealizedPl(){
        //recalculate any changes in PL and push changes to account

        System.out.println("\nnew calculation");
        System.out.println(avgBuyPrice);
        System.out.println(currentPrice);
        System.out.println(quantity);
        //first calculate the current unrealizedPL with the currentPrice and avgBuyPrice
        double newUnrealizd = (quantity)*(currentPrice - avgBuyPrice);
        System.out.println("new unrealized calculated: " + newUnrealizd + "\n");
        double difference = newUnrealizd - unrealizedProfitLoss;
        unrealizedProfitLoss = newUnrealizd;
        System.out.println("difference in unrealized calculated vs existing: " + difference);

        TradingAccount account = TradingAccount.getAccountNoRefresh(accountID);
        System.out.println("fetched account profit and loss:" + account.getUnrealizedProfitLoss());
        account.setUnrealizedProfitLoss(account.getUnrealizedProfitLoss() + difference);
        System.out.println("fetched account newly set proft and loss:" + account.getUnrealizedProfitLoss());
        TradingAccount.update(account);

        //push position change to the DB also
    }

    //does a recalculation of realizedPL based on current stock prices and pushes changes to DB
    //also updates Account for this Customer
    private void calculateRealizedPl(int quantitySold){
        //recalculate any changes in PL and push changes to account
        double newRealized = (quantitySold) * (currentPrice - avgBuyPrice);

        TradingAccount account = TradingAccount.getAccountNoRefresh(accountID);
        account.setRealizedProfitLoss(account.getRealizedProfitLoss() + newRealized);
        TradingAccount.update(account);
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(double avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public double getRealizedProfitLoss() {
        return realizedProfitLoss;
    }

    public void setRealizedProfitLoss(double realizedProfitLoss) {
        this.realizedProfitLoss = realizedProfitLoss;
    }

    public double getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(double unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public boolean isBlockedPosition(){
        return Market.getInstance().isBlocked(securityId);
    }

    //beginning of wrapper methods
    public static List<Position> getAllPositions(int accountId){
        PositionDao pDao = PositionDao.getInstance();
        return pDao.getAllPositions(accountId);
    }

    //pushes this Position back into the DB, after updating stock price and PL
    public void refresh(){
        //get the latest stock price
        setCurrentPrice(StockDao.getInstance().getStock(securityId).getPrice());
        calculateUnrealizedPl();
        PositionDao pDao = PositionDao.getInstance();
        pDao.pushToDB(this);
    }

    //refreshes any Position into the DB
    public static void refresh(Position position){
        PositionDao pDao = PositionDao.getInstance();
        pDao.updatePosition(position);
    }

    public static Position getPosition(int accountID, int securityId){
        PositionDao pDao = PositionDao.getInstance();
        return pDao.getPosition(accountID,securityId);
    }

    public static void deletePosition(int accountId, int securityId){
        PositionDao pDao = PositionDao.getInstance();
        pDao.deletePosition(accountId, securityId);
    }

    public static void deletePosition(Position position){
        PositionDao pDao = PositionDao.getInstance();
        pDao.deletePosition(position);
    }

    @Override
    public String toString() {
        return "Account ID: "+ accountID + " Security ID: "+ securityId + " Quantity: " + quantity
                + " current sell price: "+ currentPrice + " average buy price: "+
                avgBuyPrice + " realized profit or loss：" + realizedProfitLoss + " unrealized profit or loss: " +
                unrealizedProfitLoss;
    }
}
