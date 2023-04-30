package org.TradingSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransactionDao {
    private Connection connection;

    public TransactionDao(){
        connection = DatabaseConnection.getConnection();
    }

    public Transaction getTransaction(int transactionId){
        try{
            String sql = "SELECT * FROM transactions WHERE"
                    + " transactionId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, transactionId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return new Transaction(rs.getInt("transactionId"),
                        rs.getInt("accountId"),
                        rs.getInt("stockId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("timestamp"),
                        rs.getString("action"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
    public List<Transaction> getAllTransactionsByAccount(int accountId){
        try{
            String sql = "SELECT * FROM transactions WHERE"
                    + " accountId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet rs = pstmt.executeQuery();

            LinkedList<Transaction> transactions = new LinkedList<Transaction>();
            while(rs.next()){
                transactions.add(new Transaction(rs.getInt("transactionId"),
                        rs.getInt("accountId"),
                        rs.getInt("stockId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("timestamp"),
                        rs.getString("action")));
            }
            return transactions;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
    public List<Transaction> getAllTransactions(int accountId, int securityId){
        try{
            String sql = "SELECT * FROM transactions WHERE"
                    + " accountId = ? AND stockId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            pstmt.setInt(2,securityId);

            ResultSet rs = pstmt.executeQuery();

            LinkedList<Transaction> transactions = new LinkedList<Transaction>();
            while(rs.next()){
                transactions.add(new Transaction(rs.getInt("transactionId"),
                        rs.getInt("accountId"),
                        rs.getInt("stockId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("timestamp"),
                        rs.getString("action")));
            }
            return transactions;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
    public List<Transaction> getAllTransactionsByStock(int stockId){
        try{
            String sql = "SELECT * FROM transactions WHERE"
                    + " stockId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stockId);

            ResultSet rs = pstmt.executeQuery();

            LinkedList<Transaction> transactions = new LinkedList<Transaction>();
            while(rs.next()){
                transactions.add(new Transaction(rs.getInt("transactionId"),
                        rs.getInt("accountId"),
                        rs.getInt("stockId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("timestamp"),
                        rs.getString("action")));
            }
            return transactions;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
//    getAllTransactions(Timestamp1, Timestamp2) ????
    public void addTransaction(int accountId, int stockId, int quantity, double price, String action){
        String sql = "INSERT INTO people (accountId, stockId, quantity, price, timestamp, action) VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, stockId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setString(5,timestamp.toString());
            pstmt.setString(6,action);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void addTransaction(Transaction transaction){
        String sql = "INSERT INTO people (accountId, stockId, quantity, price, timestamp, action, transactionId) VALUES (?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setInt(2, transaction.getStockId());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDouble(4, transaction.getPrice());
            pstmt.setString(5,transaction.getTimestamp());
            pstmt.setString(6, transaction.getAction());
            pstmt.setInt(7,transaction.getTransactionId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void deleteTransaction(Transaction transaction){
        String sql = "DELETE FROM transactions WHERE transactionId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,transaction.getTransactionId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteTransaction(int transactionId){
        String sql = "DELETE FROM transactions WHERE transactionId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,transactionId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void updateTransaction(Transaction transaction){
        String sql = "UPDATE transactions SET accountId = ?, "
                + "stockId = ?,"
                + "quantity = ?,"
                + "price = ?,"
                + "timestamp = ?,"
                + "action = ?"
                + "WHERE personId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setInt(2, transaction.getStockId());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDouble(4, transaction.getPrice());
            pstmt.setString(5,transaction.getTimestamp());
            pstmt.setString(6, transaction.getAction());
            pstmt.setInt(7,transaction.getTransactionId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
