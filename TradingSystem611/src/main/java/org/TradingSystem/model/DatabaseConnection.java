package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

        private static Connection con = null;

        public static Connection getConnection()
        {
            if(con == null){
                try{
                    con = DriverManager.getConnection(Config.DBPATH);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return con;

        }

}
