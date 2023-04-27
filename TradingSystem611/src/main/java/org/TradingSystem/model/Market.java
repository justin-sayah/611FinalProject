package org.TradingSystem.model;

import java.util.*;

public class Market {
    public static Market market;

    private Market(){
    }

    public static Market getInstance() {
        if(market == null){
            market = new Market();
        }
        return market;
    }

    public List<Stock> getAllStocks(){
        return null;
    }

    public Stock getStock(int stockId){
        return null;
    }

    public List<Stock> getStocksByTickerSearch(String searchString){
        return null;
    }

    public void updatePrice(int stockId, int price){
        //stuff
    }
    public void updateStock(Stock stock){
        //stuff
    }
    public void deleteStock(int stockId){
        //stuff
    }
    public void deleteStock(Stock stock){
        //stuff
    }
    public void addStock(String name, int price, String ticker){
        //stuff
    }

    public void addStock(Stock stock){
        //stuff
    }
}
