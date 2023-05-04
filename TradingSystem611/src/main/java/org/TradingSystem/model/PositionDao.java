package org.TradingSystem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/*
Date: 4/23/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Data Access Object to perform CRUD on Position objects
 */
public class PositionDao {
    private Connection connection;
    public static PositionDao positionDao;

    //TODO: make private
    public PositionDao(){
        connection = DatabaseConnection.getConnection();
    }

    public static PositionDao getInstance() {
        if(positionDao == null){
            positionDao = new PositionDao();
        }
        return positionDao;
    }

    public List<Position> getAllPositions(int accountId){
        try{
            String sql = "SELECT * FROM positions WHERE"
                    + " accountId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet rs = pstmt.executeQuery();

            LinkedList<Position> positions = new LinkedList<Position>();
            StockDao sDao = StockDao.getInstance();
            while(rs.next()){
                Position fetched = new Position(accountId, rs.getInt("securityId"),rs.getInt("quantity"),
                        rs.getInt("quantitySold"), sDao.getStock(rs.getInt("securityId")).getPrice(),
                        rs.getDouble("avgBuyPrice"), rs.getDouble("realizedPL"), rs.getDouble("unrealizedPL"));
                positions.add(fetched);

                //push new price to the db, reflects price update in terms of PL in parent account as well
                updatePosition(fetched);
            }
            return positions;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public void updatePosition(Position position){
        //NEED TO DO A RECALCULATION OF PROFIT AND LOSS TO MAKE SURE ITS UP TO DATE
        //BEFORE PUSHING TO DB
        position.refresh();

        String sql = "UPDATE positions SET quantity = ? , "
                + "quantitySold = ?,"
                + "avgBuyPrice = ?,"
                + "currentSellPrice = ?," +
                "realizedPL = ?," +
                "unrealizedPL = ?"
                + "WHERE accountId = ? AND securityId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, position.getQuantity());
            pstmt.setInt(2, position.getQuantitySold());
            pstmt.setDouble(3, position.getAvgBuyPrice());
            pstmt.setDouble(4, position.getCurrentPrice());
            pstmt.setDouble(5, position.getRealizedProfitLoss());
            pstmt.setDouble(6, position.getUnrealizedProfitLoss());
            pstmt.setInt(7, position.getAccountID());
            pstmt.setInt(8, position.getSecurityId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void pushToDB(Position position){
        String sql = "UPDATE positions SET quantity = ? , "
                + "quantitySold = ?,"
                + "avgBuyPrice = ?,"
                + "currentSellPrice = ?," +
                "realizedPL = ?," +
                "unrealizedPL = ?"
                + "WHERE accountId = ? AND securityId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, position.getQuantity());
            pstmt.setInt(2, position.getQuantitySold());
            pstmt.setDouble(3, position.getAvgBuyPrice());
            pstmt.setDouble(4, position.getCurrentPrice());
            pstmt.setDouble(5, position.getRealizedProfitLoss());
            pstmt.setDouble(6, position.getUnrealizedProfitLoss());
            pstmt.setInt(7, position.getAccountID());
            pstmt.setInt(8, position.getSecurityId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void createPosition(Position position){
        String sql = "INSERT INTO positions (quantity, quantitySold, " +
                "avgBuyPrice, currentSellPrice, realizedPL, unrealizedPL, accountID, securityId)" +
                "VALUES (?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, position.getQuantity());
            pstmt.setInt(2, position.getQuantitySold());
            pstmt.setDouble(3, position.getAvgBuyPrice());
            pstmt.setDouble(4, position.getCurrentPrice());
            pstmt.setDouble(5, position.getRealizedProfitLoss());
            pstmt.setDouble(6, position.getUnrealizedProfitLoss());
            pstmt.setInt(7, position.getAccountID());
            pstmt.setInt(8, position.getSecurityId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public Position getPosition(int accountId, int securityId){
        try{
            String sql = "SELECT * FROM positions WHERE"
                    + " accountId = ? AND securityID = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, securityId);

            ResultSet rs = pstmt.executeQuery();

            StockDao sDao = new StockDao();
            if(rs.next()){
                Position fetched = new Position(accountId, securityId ,rs.getInt("quantity"),
                        rs.getInt("quantitySold"), sDao.getStock(rs.getInt("securityId")).getPrice(),
                        rs.getDouble("avgBuyPrice"), rs.getDouble("realizedPL"), rs.getDouble("unrealizedPL"));
                updatePosition(fetched);
                return fetched;
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public void deletePosition(int accountId, int securityId){
        String sql = "DELETE FROM positions WHERE accountId = ?" +
                "AND securityId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, securityId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deletePosition(Position position){
        deletePosition(position.getAccountID(), position.getSecurityId());
    }
}
