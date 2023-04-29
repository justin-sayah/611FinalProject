package org.TradingSystem.model;

public abstract class  Security implements Tradeable{
    private String name;
    private double price;
    private int securityId;

    public Security(int securityId, String name, double price){
        setSecurityId(securityId);
        setName(name);
        changePrice(price);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changePrice(double price) {
        this.price = price;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getSecurityId() {
        return securityId;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + " Price: " + getPrice();
    }
}
