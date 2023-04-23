package org.TradingSystem.model;

import java.sql.*;
import java.util.List;

public class DBTesting {
//    public static void connect() {
//        Connection conn = null;
//        try {
//             create a connection to the database
//            conn = DatabaseConnection.getConnection();
//            conn.createStatement()
//
//            String sql = "SELECT * FROM activeAccounts";
//
//            Statement stmt  = conn.createStatement();
//            ResultSet rs    = stmt.executeQuery(sql);
//
//             loop through the result set
//            while (rs.next()) {
//                System.out.println(rs.getString("type"));
//            }
//
//            System.out.println("Connection to SQLite has been established.");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Trying to get one account
        TradingAccountDao tradingAccountDao = new TradingAccountDao();
        TradingAccount testAccount = tradingAccountDao.getAccount(1,1);
        System.out.println(testAccount);

        System.out.println("Printing all accounts for customerId 1 \n\n");

        //trying to get all accounts
        List<TradingAccount> accounts = tradingAccountDao.getAllActive(1);
        for(TradingAccount account: accounts){
            System.out.println(account);
        }
    }
}
