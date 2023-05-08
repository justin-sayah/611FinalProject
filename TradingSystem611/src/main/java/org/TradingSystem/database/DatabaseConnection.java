package org.TradingSystem.database;

import java.sql.Connection;
import java.sql.DriverManager;

//Maintains a connection to the persistent storage mechanism
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
