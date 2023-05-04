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
//        MessageCenter messageCenter = MessageCenter.getInstance();
//        System.out.println(messageCenter.getMessagesInInbox(1));
//        System.out.println();
//        //send a message from 1 to 2
//        messageCenter.sendMessage(1,2,"hello! I just sent this now!");
//        //print out all messsages for 2
//        System.out.println();
//        List<Message> messages = messageCenter.getMessagesInInbox(2);
//        for (Message message:
//             messages) {
//            System.out.println(message);
//            //deleting this message
//            System.out.println("\ndeleting this message\n");
//            messageCenter.deleteMessage(message);
//        }
//
//        //check to see whats in inbox for 2
//        List<Message> messages2 = messageCenter.getMessagesInInbox(2);
//        for (Message message:
//                messages2) {
//            System.out.println(message);
//        }

        //testing people
//        Customer test = new Customer(999, "Justin", "Sayah","jsayah", "611project", "09/30/2000", "000-00-0001");
//        PeopleDao peopleDao = new PeopleDao();
//        peopleDao.addCustomer(test);
//        System.out.println("made it here");
//
//        //retrieve to see if this person was added and print them out
//        System.out.println(peopleDao.getCustomer(999));
//        System.out.println("after fetching");
////        //also try logging in to see if it is retrieved
//        System.out.println(peopleDao.login("jsayah", "611project"));
////
////
//        //try deleting person from database
//        peopleDao.deleteCustomer(test);
//        //retrieve to see if this person was added and print them out
//        System.out.println("should print out null");
//        System.out.println(peopleDao.getCustomer(999));


        //testing positions
//        PositionDao positionDao = new PositionDao();
//        Position p = positionDao.getAllPositions(1).get(0);
//        System.out.println(positionDao.getAllPositions(1));
//
//        System.out.println();
//
//        p.setQuantity(11);
//        positionDao.updatePosition(p);
//
//        System.out.println(positionDao.getPosition(1,1));


        //testing stocks
//        Stock s1 = new Stock(1,100,"Apple Inc", "AAPL");
//        Stock s2 = new Stock(2, 15, "AMC Entertainment", "AMC");
//        StockDao stockDao = new StockDao();
//        stockDao.addStock(s1);
//        stockDao.addStock(s2);
//
//        System.out.println(stockDao.getStock(1));
//        System.out.println();
//        System.out.println(stockDao.getStocks("a"));

//        TradingAccount tradingAccount = new TradingAccount(3,1,10000,400,500);
//        TradingAccount tradingAccount = TradingAccount.getAccount(1);
//        TradingAccountDao.getInstance().blockAccount(tradingAccount);
//        System.out.println(tradingAccount);
        Market.updateAllPricesRealLife();
    }

}
