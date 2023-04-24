package org.TradingSystem.model;

import java.util.*;

/*
Date: 4/22/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Singleton object to abstract receiving and sending messages to clients
 */
public class MessageCenter {
    public static MessageCenter messageCenter;
    private MessageDao messageDao;

    private MessageCenter(){
        messageDao = new MessageDao();
    }

    public static MessageCenter getInstance() {
        if(messageCenter == null){
            messageCenter = new MessageCenter();
        }
        return messageCenter;
    }

    public void sendMessage(int customerIdFrom, int customerIdTo, String message){
        messageDao.postMessage(customerIdFrom, customerIdTo, message);
    }

    public List<Message> getMessagesInInbox(int customerId){
        //invoke MessageDao to get all messages as a list
        return messageDao.getMessages(customerId);
    }

    public void deleteMessage(Message message){
        messageDao.deleteMessage(message);
    }
}
