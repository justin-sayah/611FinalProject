package org.TradingSystem.model;

import org.TradingSystem.database.MessageDao;

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
        messageDao = MessageDao.getInstance();
    }

    public static MessageCenter getInstance() {
        if(messageCenter == null){
            messageCenter = new MessageCenter();
        }
        return messageCenter;
    }

    public void sendMessage(int customerIdFrom, int personIdTo, String message){
        messageDao.postMessage(customerIdFrom, personIdTo, message);
    }

    public List<Message> getMessagesInInbox(int personId){
        //invoke MessageDao to get all messages as a list
        return messageDao.getMessages(personId);
    }

    public void deleteMessage(Message message){
        messageDao.deleteMessage(message);
    }

    public void deleteMessage(int messageId){
        messageDao.deleteMessage(messageId);
    }
}
