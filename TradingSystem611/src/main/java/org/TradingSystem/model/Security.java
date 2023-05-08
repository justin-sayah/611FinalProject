package org.TradingSystem.model;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Abstract class representing the data and behavior of any generic type of Security
 */
public abstract class  Security implements Tradeable{
    private String name;
    private double price;
    private int securityId;

    public Security(int securityId, String name, double price){
        setSecurityId(securityId);
        setName(name);
        setPrice(price);
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

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSecurityId() {
        return securityId;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + " Price: " + getPrice();
    }
}
