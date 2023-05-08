package org.TradingSystem.model;

import org.TradingSystem.database.MessageDao;
import org.TradingSystem.database.PeopleDao;

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
        return PeopleDao.StockDao.getInstance().getAllStocks();
    }

    public List<Stock> getAllUnblockedStocks(){
        return PeopleDao.StockDao.getInstance().getAllUnblockedStocks();
    }

    public List<Stock> getAllBlockedStocks(){
        return PeopleDao.StockDao.getInstance().getAllBlockedStocks();
    }

    public void blockStock(int stockId){
        PeopleDao.StockDao.getInstance().blockStock(stockId);
    }

    public void blockStock(Stock stock){
        PeopleDao.StockDao.getInstance().blockStock(stock);
    }

    public void unblockStock(int stockId){
        PeopleDao.StockDao.getInstance().unblockStock(stockId);
    }

    public void unblockStock(Stock stock){
        PeopleDao.StockDao.getInstance().unblockStock(stock);
    }

    public boolean isBlocked(int stockId){
        return PeopleDao.StockDao.getInstance().isBlocked(stockId);
    }

    public Stock getStock(int stockId){
        return PeopleDao.StockDao.getInstance().getStock(stockId);
    }

    public List<Stock> getStocksByTickerSearch(String searchString){
        return PeopleDao.StockDao.getInstance().getStocks(searchString);
    }

    public void updatePrice(int stockId, double price){
        Stock stock = getStock(stockId);
        stock.changePrice(price);
    }

    //uses API to update all prices by IRL prices if possible
    public static void updateAllPricesRealLife(){
        List<Stock> stocks = getInstance().getAllStocks();
        for(Stock stock: stocks){
            Double realPrice = MessageDao.PriceFetcher.fetchPrice(stock.getTicker());
            if(realPrice != null){
                stock.changePrice(realPrice);
            }
        }
    }

    public static void updatePriceRealLife(int stockId){
        Stock stock = PeopleDao.StockDao.getInstance().getStock(stockId);
        Double realPrice = MessageDao.PriceFetcher.fetchPrice(stock.getTicker());
        if(realPrice != null){
            stock.changePrice(realPrice);
        }

    }

    //make sure that update is pushed to stock in either table
    public void updateStock(Stock stock){
        PeopleDao.StockDao.getInstance().updateStock(stock);
    }
    public void deleteStock(int stockId){
        PeopleDao.StockDao.getInstance().deleteStock(stockId);
    }
    public void deleteStock(Stock stock){
        PeopleDao.StockDao.getInstance().deleteStock(stock);
    }
    public void addStock(String name, double price, String ticker){
        PeopleDao.StockDao.getInstance().addStock(name, price, ticker);
    }
    public void addStock(Stock stock){
        PeopleDao.StockDao.getInstance().addStock(stock);
    }
}
