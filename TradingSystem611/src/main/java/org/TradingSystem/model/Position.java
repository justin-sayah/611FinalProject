package org.TradingSystem.model;

import javafx.geometry.Pos;

import java.util.List;

public class Position {
    protected int accountID;
    protected int quantity;
    protected double currentSellPrice;
    protected double avgBuyPrice;
    protected double realizedProfitLoss;
    protected double unrealizedProfitLoss;
    protected int securityId;
    protected int quantitySold;

    public Position(int accountID, int securityId, int quantity, int quantitySold, double currentSellPrice, double avgBuyPrice, double realizedProfitLoss, double unrealizedProfitLoss) {
        this.accountID = accountID;
        this.quantity = quantity;
        this.currentSellPrice = currentSellPrice;
        this.avgBuyPrice = avgBuyPrice;
        this.realizedProfitLoss = realizedProfitLoss;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.securityId = securityId;
        this.quantitySold = quantitySold;

        //make sure calculations on PL are up-to-date
        calculateUnrealizedPl();
        calculateRealizedPl();
        updatePosition();
    }

    //adds quantityToAdd shares at the current sellPrice/buyPrice
    //creates a transaction to cement this purchase
    //TODO: update balance after a successful purchase
    public void addToPosition(int quantityToAdd){
        //how much this purchase cost
        double newPurchaseCost = quantityToAdd*getCurrentSellPrice();
        quantity += quantityToAdd;
        avgBuyPrice = (avgBuyPrice + newPurchaseCost)/(quantity);

        calculateRealizedPl();
        calculateUnrealizedPl();


        //TODO: make a transaction record

        updatePosition();
    }

    private void calculateUnrealizedPl(){
        unrealizedProfitLoss = (quantity)*(currentSellPrice - avgBuyPrice);
    }

    private void calculateRealizedPl(){
        realizedProfitLoss = (quantitySold) *(currentSellPrice - avgBuyPrice);
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

    public double getCurrentSellPrice() {
        return currentSellPrice;
    }

    public void setCurrentSellPrice(double currentSellPrice) {
        this.currentSellPrice = currentSellPrice;
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

    //attempts to sell quantityToSell, updates PL and DB, and if quantity is 0, closes position in DB
    //TODO: update balance of Account after a successful sell
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
            calculateRealizedPl();
            calculateUnrealizedPl();

            //TODO: create a Transaction to mark sell
            if(quantity == 0){
                //TODO: delete from DB and do appropriate updating of account PL
            }
        }
    }

    //beginning of wrapper methods
    public static List<Position> getAllPositions(int accountId){
        PositionDao pDao = PositionDao.getInstance();
        return pDao.getAllPositions(accountId);
    }

    public void updatePosition(){
        PositionDao pDao = PositionDao.getInstance();
        pDao.updatePosition(this);
    }

    public static void updatePosition(Position position){
        PositionDao pDao = PositionDao.getInstance();
        pDao.updatePosition(position);
    }

    //TODO: logic of subtracting cost from account
    public static void createPosition(int accountId, int stockId, int quantity){
        StockDao sDao = new StockDao();
        double price = sDao.getStock(stockId).getPrice();
        Position p1 = new Position(accountId, stockId, quantity, 0, price, price,0, 0);
        PositionDao pDao = PositionDao.getInstance();
        pDao.createPosition(p1);
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
                + " current sell price: "+ currentSellPrice + " average buy price: "+
                avgBuyPrice + " realized profit or loss：" + realizedProfitLoss + " unrealized profit or loss: " +
                unrealizedProfitLoss;
    }
}
