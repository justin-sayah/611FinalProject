package org.TradingSystem.model;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Interface for all Tradeable objects
 */
public interface Tradeable {
    double getPrice();
    void changePrice(double price);
}
