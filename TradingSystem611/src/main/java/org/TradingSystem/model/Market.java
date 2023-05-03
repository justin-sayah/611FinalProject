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
        return StockDao.getInstance().getAllStocks();
    }

    public Stock getStock(int stockId){
        return StockDao.getInstance().getStock(stockId);
    }

    public List<Stock> getStocksByTickerSearch(String searchString){
        return StockDao.getInstance().getStocks(searchString);
    }

    public void updatePrice(int stockId, double price){
        Stock stock = getStock(stockId);
        stock.changePrice(price);
        updateStock(stock);
    }
    public void updateStock(Stock stock){
        StockDao.getInstance().updateStock(stock);
    }
    public void deleteStock(int stockId){
        StockDao.getInstance().deleteStock(stockId);
    }
    public void deleteStock(Stock stock){
        StockDao.getInstance().deleteStock(stock);
    }
    public void addStock(String name, double price, String ticker){
        StockDao.getInstance().addStock(name, price, ticker);
    }
    public void addStock(Stock stock){
        StockDao.getInstance().addStock(stock);
    }
}
