package org.TradingSystem.model;

import org.TradingSystem.database.StockDao;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Object representing data and behaviors of Stocks
 */
public class Stock extends Security{

    private String ticker;

    public Stock(int stockId, double price, String name, String ticker){
        super(stockId, name, price);
        setTicker(ticker);
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    //changes price of this Stock and pushes change to DB
    @Override
    public void changePrice(double price) {
        super.changePrice(price);
        //push change to DB
        StockDao.getInstance().updateStock(this);
    }

    //gets price from database, whatever it may be
    public void refresh(){
        StockDao.getInstance().refreshStock(this);
    }

    @Override
    public String toString() {
        return getName() + " Ticker: " + getTicker() + "  Current Price: " + getPrice();
    }
}
