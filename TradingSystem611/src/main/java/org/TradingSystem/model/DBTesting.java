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
//        //Trying to get one account
//        TradingAccountDao tradingAccountDao = new TradingAccountDao();
//        TradingAccount testAccount = tradingAccountDao.getAccount(1,1);
//        System.out.println(testAccount);
//
//        testAccount.deposit(5);
//        tradingAccountDao.update(testAccount);
//        System.out.println(testAccount);
//
//        System.out.println("Printing all accounts for customerId 1 \n\n");
//
//        //trying to get all accounts
//        List<TradingAccount> accounts = tradingAccountDao.getAllActive(1);
//        for(TradingAccount account: accounts){
//            System.out.println(account);
//        }
//
//        //make a pending account for customer 1
//        tradingAccountDao.addPendingAccount(1,"tradingAccount");
//        System.out.println("\nPrinting all pending accounts for customerId 1\n");
//        //try printing out the pending accounts
//        for (TradingAccount pending: tradingAccountDao.getAllPending(1)){
//            System.out.println(pending);
//        }

        //testing messages

        //get all messages for customerId 1 and print them
        MessageCenter messageCenter = MessageCenter.getInstance();
        System.out.println(messageCenter.getMessagesInInbox(1));
        System.out.println();
        //send a message from 1 to 2
        messageCenter.sendMessage(1,2,"hello! I just sent this now!");
        //print out all messsages for 2
        System.out.println();
        List<Message> messages = messageCenter.getMessagesInInbox(2);
        for (Message message:
             messages) {
            System.out.println(message);
            //deleting this message
            System.out.println("\ndeleting this message\n");
            messageCenter.deleteMessage(message);
        }

        //check to see whats in inbox for 2
        List<Message> messages2 = messageCenter.getMessagesInInbox(2);
        for (Message message:
                messages2) {
            System.out.println(message);
        }

    }
}
