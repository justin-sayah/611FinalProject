package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDao {
    private Connection connection;

    public StockDao(){
        connection = DatabaseConnection.getConnection();
    }

    public Stock getStock(int stockId){
        try{
            String sql = "SELECT * FROM stocks WHERE stockId = ? ";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Stock(stockId, rs.getDouble("price"), rs.getString("name"), rs.getString("ticker") );
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public void addStock(String name, int price, String ticker){
        String sql = "INSERT INTO stocks (name, " +
                "price, ticker)" +
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
    public void deleteStock(int stockId){
        String sql = "DELETE FROM stocks WHERE stockId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteStock(Stock stock){
        deleteStock(stock.getSecurityId());
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
    public void updateStock(Stock stock){
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
}
