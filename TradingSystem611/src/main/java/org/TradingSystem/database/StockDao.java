package org.TradingSystem.database;

import org.TradingSystem.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDao {
    private Connection connection;
    public static StockDao stockDao;

    private StockDao(){
        connection = DatabaseConnection.getConnection();
    }

    public static StockDao getInstance() {
        if(stockDao == null){
            stockDao = new StockDao();
        }
        return stockDao;
    }

    public void refreshStock(Stock stock){
        Stock fresh = getStock(stock.getSecurityId());
        stock.changePrice(fresh.getPrice());
    }

    //gets the stock, whether it is blocked or unblocked
    public Stock getStock(int stockId){
        try{
            String sql = "SELECT * FROM stocks WHERE stockId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Stock(stockId, rs.getDouble("price"), rs.getString("name"), rs.getString("ticker") );
            } else{
                String sql2 = "SELECT * FROM blockedStocks WHERE stockId = ? ";

                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setInt(1, stockId);

                ResultSet rs2 = pstmt2.executeQuery();
                if(rs2.next()){
                    return new Stock(stockId, rs2.getDouble("price"), rs2.getString("name"), rs2.getString("ticker") );
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //Returns all unblocked and blocked stocks
    public List<Stock> getAllStocks(){
        List<Stock> allUnblocked = getAllUnblockedStocks();
        List<Stock> allBlocked = getAllBlockedStocks();

        allUnblocked.addAll(allBlocked);
        return allUnblocked;
    }

    //returns only unblocked stocks
    public List<Stock> getAllUnblockedStocks(){
        try{
            String sql = "SELECT * FROM stocks";

            PreparedStatement pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            ArrayList<Stock> stocks = new ArrayList<Stock>();
            while(rs.next()){
                stocks.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
            }
            return stocks;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //returns only blocked stocks
    public List<Stock> getAllBlockedStocks(){
        try{
            String sql = "SELECT * FROM blockedStocks";

            PreparedStatement pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            ArrayList<Stock> stocks = new ArrayList<Stock>();
            while(rs.next()){
                stocks.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
            }
            return stocks;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //adds a stock to unblocked list
    public void addStock(String name, double price, String ticker){
        String sql = "INSERT INTO stocks (name, price, ticker)" +
                "VALUES (?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, ticker);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void updateStockPrice(Stock stock, double price){
        String sql = "UPDATE stocks SET price = ? "
                + "WHERE stockId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, price+"");
            pstmt.setDouble(2, stock.getSecurityId());


            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    //adds a stock to unblocked list
    public void addStock(Stock stock){
        String sql = "INSERT INTO stocks (stockId, name, " +
                "price, ticker)" +
                "VALUES (?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,stock.getSecurityId());
            pstmt.setString(2, stock.getName());
            pstmt.setDouble(3, stock.getPrice());
            pstmt.setString(4, stock.getTicker());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    //removes a stock from unblocked list and moves it to blocked list
    public void blockStock(Stock stock){

        //first remove from stocks table
        deleteFromUnblocked(stock.getSecurityId());

        //then add to blockedStock table
        String sql = "INSERT INTO blockedStocks (stockId, name, " +
                "price, ticker)" +
                "VALUES (?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,stock.getSecurityId());
            pstmt.setString(2, stock.getName());
            pstmt.setDouble(3, stock.getPrice());
            pstmt.setString(4, stock.getTicker());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void blockStock(int stockId){
        Stock toBlock = getStock(stockId);
        blockStock(toBlock);
    }

    //removes a stock from blocked list and moves it back to unblocked list
    public void unblockStock(Stock stock){

        System.out.println(stock);

        //delete from blocked table
        deleteFromBlocked(stock.getSecurityId());

        //add to unblocked stocks
        addStock(stock);

    }

    public void unblockStock(int stockId){
        //get the stock
        Stock toUnblock = getStock(stockId);

        unblockStock(toUnblock);
    }

    //removes a stock from both blocked and unblocked list
    public void deleteStock(int stockId){
        //TODO: Sell positions for all people that own this stock
        deleteFromUnblocked(stockId);
        deleteFromBlocked(stockId);
    }

    //removes stock from unblocked and blocked list
    public void deleteStock(Stock stock){
        deleteStock(stock.getSecurityId());
    }

    //deletes a stock from unblocked list
    private void deleteFromUnblocked(int stockId){
        String sql = "DELETE FROM stocks WHERE stockId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public boolean isBlocked(int stockId){
        try{
            String sql = "SELECT * FROM stocks WHERE stockId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return false;
            } else{
                return true;
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    //deletes a stock from blocked list
    private void deleteFromBlocked(int stockId){
        String sql = "DELETE FROM blockedStocks WHERE stockId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public List<Stock> getStocks(String tickerQuery){
        try{
            String sql = "SELECT * FROM stocks WHERE"
                    + " ticker LIKE ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tickerQuery + "%");

            ResultSet rs = pstmt.executeQuery();

            ArrayList<Stock> accounts = new ArrayList<Stock>();
            while(rs.next()){
                accounts.add(new Stock(rs.getInt("stockId"), rs.getDouble("price"), rs.getString("name"), rs.getString("ticker")));
            }
            return accounts;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    //updates stock regardless of if it is in blocked or unblocked state
    public void updateStock(Stock stock){
        updateUnblockedStock(stock);
        updateBlockedStock(stock);
    }

    private void updateUnblockedStock(Stock stock){
        String sql = "UPDATE stocks SET ticker = ? , "
                + "price = ?, name = ?"
                + "WHERE stockId = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, stock.getTicker());
            pstmt.setDouble(2, stock.getPrice());
            pstmt.setString(3, stock.getName());
            pstmt.setInt(4, stock.getSecurityId());


            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void updateBlockedStock(Stock stock){
        String sql = "UPDATE blockedStocks SET ticker = ? , "
                + "price = ?, name = ?"
                + "WHERE stockId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, stock.getTicker());
            pstmt.setDouble(2, stock.getPrice());
            pstmt.setString(3, stock.getName());
            pstmt.setInt(4, stock.getSecurityId());


            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
