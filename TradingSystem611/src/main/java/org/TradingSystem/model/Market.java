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

    public List<Stock> getAllUnblockedStocks(){
        return StockDao.getInstance().getAllUnblockedStocks();
    }

    public List<Stock> getAllBlockedStocks(){
        return StockDao.getInstance().getAllBlockedStocks();
    }

    public void blockStock(int stockId){
        StockDao.getInstance().blockStock(stockId);
    }

    public void blockStock(Stock stock){
        StockDao.getInstance().blockStock(stock);
    }

    public void unblockStock(int stockId){
        StockDao.getInstance().unblockStock(stockId);
    }

    public void unblockStock(Stock stock){
        StockDao.getInstance().unblockStock(stock);
    }

    public boolean isBlocked(int stockId){
        return StockDao.getInstance().isBlocked(stockId);
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
    }

    //uses API to update all prices by IRL prices if possible
    public static void updateAllPricesRealLife(){
        List<Stock> stocks = getInstance().getAllStocks();
        for(Stock stock: stocks){
            Double realPrice = PriceFetcher.fetchPrice(stock.getTicker());
            if(realPrice != null){
                stock.changePrice(realPrice);
            }
        }
    }

    //make sure that update is pushed to stock in either table
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
