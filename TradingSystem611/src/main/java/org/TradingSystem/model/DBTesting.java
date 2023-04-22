package org.TradingSystem.model;

import java.sql.*;

public class DBTesting {
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            System.out.println();
            String url = "jdbc:sqlite:TradingSystem611\\src\\main\\java\\org\\TradingSystem\\database\\chinook.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
//            conn.createStatement()

            String sql = "SELECT * FROM albums";

            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Title"));
            }

            System.out.println("Connection to SQLite has been established.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
}
