package org.TradingSystem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

    public PositionDao(){
        connection = DatabaseConnection.getConnection();
    }

    public List<Position> getAllPositions(int accountId){
        try{
            String sql = "SELECT * FROM positions WHERE"
                    + " accountId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet rs = pstmt.executeQuery();

            LinkedList<Position> accounts = new LinkedList<Position>();
            while(rs.next()){
                accounts.add(new Position(accountId, rs.getInt("securityId"),rs.getInt("quantity"),
                        rs.getInt("quantitySold"), rs.getDouble("currentSellPrice"),
                        rs.getDouble("avgBuyPrice"), rs.getDouble("realizedPL"), rs.getDouble("unrealizedPL")));
            }
            return accounts;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public void updatePosition(Position position){
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
            pstmt.setDouble(4, position.getCurrentSellPrice());
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
            pstmt.setDouble(4, position.getCurrentSellPrice());
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

            if(rs.next()){
                return new Position(accountId, securityId ,rs.getInt("quantity"),
                        rs.getInt("quantitySold"), rs.getDouble("currentSellPrice"),
                        rs.getDouble("avgBuyPrice"), rs.getDouble("realizedPL"), rs.getDouble("unrealizedPL"));
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
