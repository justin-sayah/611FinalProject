package org.TradingSystem.model;

import org.TradingSystem.views.LoginFrame;

/*
Date: 5/8/23
Class: CS611 Final Project
Author: 611 Team 4
Purpose: Entry point for system
 */
public class StockTradingSystem {

    public void launchSystem(){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}
