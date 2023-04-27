package org.TradingSystem.model;

public class Stock extends Security{

    private String ticker;

    public Stock(int stockId, int price, String name, String ticker){
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
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public void changePrice(int price) {
        super.changePrice(price);
    }
}
