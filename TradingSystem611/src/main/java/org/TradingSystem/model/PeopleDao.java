package org.TradingSystem.model;

public class PeopleDao {

    private Connecton connecton;

    public PeopleDao(){
        connecton = DatabaseConnection.getConnection();
    }


}
