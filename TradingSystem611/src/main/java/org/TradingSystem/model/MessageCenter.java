package org.TradingSystem.model;

import java.util.ArrayList;

public class MessageCenter {

    public MessageCenter(){

    }
    public void sendMessage(Customer customer, Message message){
        customer.receiveMessage(message);
    }
    public ArrayList<Message> getAllMessages(Customer customer){
        return customer.getAllMessages();
    }
}
