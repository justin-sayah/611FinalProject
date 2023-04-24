package org.TradingSystem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class PositionDao {
    private Connection connection;

    public PositionDao(){
        connection = DatabaseConnection.getConnection();
    }

    public Position getAllPositions(int accountId){
        return null;
    }

    public void updatePosition(Position position){

    }

    public Position getPosition(int accountId, int securityId){
        return null;
    }
}
