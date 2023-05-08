package org.TradingSystem.database;
import org.TradingSystem.model.Message;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
/*
Date: 4/23/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Data Access Object to perform CRUD on Messages across DB
 */
public class MessageDao {

    private Connection connection;

    public static MessageDao messageDao;

    private MessageDao(){
        connection = DatabaseConnection.getConnection();
    }

    public static MessageDao getInstance() {
        if(messageDao == null){
            messageDao = new MessageDao();
        }
        return messageDao;
    }


    public ArrayList<Message> getMessages(int customerId){
        try{
            String sql = "SELECT * FROM messages WHERE"
                    + " customerIdTo = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customerId);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<Message> messages = new ArrayList<Message>();
            while(rs.next()){
                messages.add(new Message(rs.getInt("messageId"), rs.getInt("customerIdFrom"), rs.getInt("customerIdTo"), rs.getString("message")));
            }
            return messages;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public void postMessage(int customerIdFrom, int customerIdTo, String message){
        try{
            String sql = "INSERT INTO messages (customerIdFrom, customerIdTo, message)" +
                    " VALUES (?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,customerIdFrom);
            pstmt.setInt(2, customerIdTo);
            pstmt.setString(3, message);

            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteMessage(Message message){
        String sql = "DELETE FROM messages WHERE messageId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,message.getMessageId());

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteMessage(int messageId){
        String sql = "DELETE FROM messages WHERE messageId = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,messageId);

            pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
