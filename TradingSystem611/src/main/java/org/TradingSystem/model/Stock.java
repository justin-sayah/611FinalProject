package org.TradingSystem.model;

import org.TradingSystem.database.PeopleDao;

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

    @Override
    public void changePrice(double price) {
        super.changePrice(price);
        //push change to DB
        PeopleDao.StockDao.getInstance().updateStock(this);
    }

    //gets price from database, whatever it may be
    public void refresh(){
        PeopleDao.StockDao.getInstance().refreshStock(this);
    }

    @Override
    public String toString() {
        return getName() + " Ticker: " + getTicker() + "  Current Price: " + getPrice();
    }
}
