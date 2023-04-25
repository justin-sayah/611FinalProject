package org.TradingSystem.model;

public class Position {
    protected int accountID;
    protected int quantity;
    protected double currentSellPrice;
    protected double avgBuyPrice;
    protected double realizedProfitLoss;
    protected double unrealizedProfitLoss;
    protected int securityId;
    protected int quantitySold;

    public Position(int accountID, int quantity, double currentSellPrice, double avgBuyPrice, double realizedProfitLoss, double unrealizedProfitLoss, int securityId, int quantitySold) {
        this.accountID = accountID;
        this.quantity = quantity;
        this.currentSellPrice = currentSellPrice;
        this.avgBuyPrice = avgBuyPrice;
        this.realizedProfitLoss = realizedProfitLoss;
        this.unrealizedProfitLoss = unrealizedProfitLoss;
        this.securityId = securityId;
        this.quantitySold = quantitySold;
    }

    public void addToPosition(int quantityToAdd, double buyPrice){
        double totalCost = avgBuyPrice * quantity + buyPrice * quantityToAdd;
        quantity += quantityToAdd;
        avgBuyPrice = totalCost/quantity;
    }

    public void updatePosition(){
        PositionDao pd = new PositionDao();
        pd.updatePosition(this);
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
        return quantitySold *(currentSellPrice - avgBuyPrice);
    }

    public void setRealizedProfitLoss(double realizedProfitLoss) {
        this.realizedProfitLoss = realizedProfitLoss;
    }

    public double getUnrealizedProfitLoss() {
        return (quantity - quantitySold)*(currentSellPrice - avgBuyPrice);
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

    public void sell(){
        if(quantitySold < quantity){
            quantity -= quantitySold;
        }else{
            System.out.println("Quantity to sell is greater than the quantity that customer has");
        }
    }
    @Override
    public String toString() {
        return "Account ID: "+ accountID + " Security ID: "+ securityId + " Quantity: " + quantity
                + " current sell price: "+ currentSellPrice + " average buy price: "+
                avgBuyPrice + " realized profit or loss：" + realizedProfitLoss + " unrealized profit or loss: " +
                unrealizedProfitLoss;
    }
}
