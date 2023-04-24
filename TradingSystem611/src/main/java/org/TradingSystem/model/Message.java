package org.TradingSystem.model;

/*
Date: 4/23/23
Class: CS611 Final Project
Author: Justin Sayah, jsayah@bu.edu
Purpose: Object representing behavior of a Message
 */
public class Message {
    private String message;
    private int messageId;
    private int customerIdFrom;
    private int customerIdTo;

    public Message(int messageId, int customerIdFrom, int customerIdTo, String message){
        setMessage(message);
        setMessageId(messageId);
        setCustomerIdFrom(customerIdFrom);
        setCustomerIdTo(customerIdTo);
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setCustomerIdFrom(int customerIdFrom) {
        this.customerIdFrom = customerIdFrom;
    }

    public void setCustomerIdTo(int customerIdTo) {
        this.customerIdTo = customerIdTo;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public int getCustomerIdFrom() {
        return customerIdFrom;
    }

    public int getCustomerIdTo() {
        return customerIdTo;
    }

    @Override
    public String toString() {
        return "To CustomerID: " + customerIdTo + "\n" +
                        "From CustomerID: " + customerIdFrom + "\n" +
                        "Body: " + message;
    }
}
