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
    private int personIdFrom;
    private int personIdTo;

    public Message(int messageId, int personIdFrom, int personIdTo, String message){
        setMessage(message);
        setMessageId(messageId);
        setPersonIdFrom(personIdFrom);
        setPersonIdTo(personIdTo);
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setPersonIdFrom(int personIdFrom) {
        this.personIdFrom = personIdFrom;
    }

    public void setPersonIdTo(int personIdTo) {
        this.personIdTo = personIdTo;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public int getPersonIdFrom() {
        return personIdFrom;
    }

    public int getPersonIdTo() {
        return personIdTo;
    }

    @Override
    public String toString() {
        return "To CustomerID: " + personIdTo + "\n" +
                        "From CustomerID: " + personIdFrom + "\n" +
                        "Body: " + message;
    }
}
