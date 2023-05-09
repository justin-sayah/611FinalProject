# 611FinalProject
Group project trading system for CS611

Link for initial design doc: https://app.sketchtogether.com/s/sketch/Z-Jdw.1.1/

Frontend Design: https://app.sketchtogether.com/s/sketch/-cO6T.26.6/

---------------------------------------------------------------------------

# Group member information

Justin Sayah: 
jsayah@bu.edu
U67474020

Shangzhou Yin: 
syin10@bu.edu
U63027471

Junru He: 
hjryy@bu.edu
U44804866

Haiwei Sun: 
hsun1239@bu.edu
U66364483

---------------------------------------------------------------------------

# Class Descriptions
Account.java - interface that enables to create different types of accounts.

AccountDao.java - interface for an abstract DAO used to perform CRUD (Create, Read, Update, Delete) operations on accounts.

AddStockFrame.java - create a GUI window for adding a new stock to the trading system.

ApproveAccountFrame.java - a GUI frame for the bank manager to approve pending trading accounts.

BlockAccountFrame.java - a GUI frame that allows a manager to block accounts for a customer.

BlockedAccountPage.java - display all blocked trading accounts.

BlockedAccountsWithdrawPopup.java - a pop-up window that allows the user to withdraw funds from a blocked trading account.

BlockSellPage.java - a GUI for the "Sell/Manage Stocks" page of blocked accounts.

BuyConfirmPopup.java - a GUI for displaying a pop-up window when a customer wants to confirm a stock purchase.

BuyStockPage.java - a GUI for the customer to buy stocks.

CustomerAccountView.java - a GUI for the customer account view in a trading system.

CustomerHomePage.java - a GUI for the customer home main page.

DatabaseConnection.java - used for DAO classes to obtain a connection to the database.

DBTesting.java - includes testing code for connecting to the database.

DeleteAccountFrame.java - a GUI that enables the manager to delete trading accounts.

DepositPopup.java - a JDialog that allows the user to add funds to their trading account.

LoginFrame.java - a GUI for the login functionality of the trading system.

Main.java - connects to a SQLite database using JDBC API.

ManageAccountFrame.java - a GUI allows a manager to manage customer accounts.

ManageMarketFrame.java - a GUI for managing stocks in a trading system

Manager.java - a class for managing trading accounts and customers.

ManagerFrame.java - a GUI for manager main page.

Market.java - a class represents the trading market where stocks are bought and sold.

Message.java - a class defines the structure of a message sent between customers and managers.

MessageCenter.java - a class for managing the messaging system between customers and managers.

MessageDao.java - Dao that provides methods to interact with the database for CRUD operations on messages.

MessagePopup.java - a pop-up window in a Trading System application that displays messages received by a customer.

PeopleDao.java - Dao that provides methods for manipulating data in the "people" table of the database.

Person.java - a superclass of the Customer and Manager classes.

Position.java - a class representing a trading position.

PositionDao.java - Dao provides methods for interacting with a database on Position objects.

PriceFetcher.java - fetch the current price of a stock using an external API.

Security.java - an abstract class that serves as the parent class for all security types.

SellManageFrame.java - a GUI window for a customer to sell/manage their stocks.

SignUpFrame.java - a GUI for customer to sign up.

Stock.java - a class represents a stock in the trading system.

StockTradingSystem.java - launches the trading program by creating a login frame on the screen

StockDao.java - DAO that provides methods for interacting with the database of Stock objects.

SystemDriver.java - holds concise main to launch the program within StockTradingSystem

Tradeable.java - an interface provides a contract for objects that can be traded.

TradingAccount.java - a trading account, which implements the Account interface.

TradingAccountDao.java - DAO that provides methods for interacting with the database of TradingAccount objects.

Transaction.java - a class represents a single transaction made by a trading account on a specific stock.

TransactionDao.java - Dao that is responsible for handling the database operations related to the Transaction class.

TransactionHistoryFrame.java - a GUI class that displays the transaction history for a trading account.

UnblockAccountFrame.java - GUI allows a manager to unblock accounts for a customer.

ViewAccountsFrame.java - GUI to view the trading accounts of a customer

ViewActiveAccountsFrame.java - GUI window that displays a table of all active trading accounts.

ViewBlockedAccounts.java - GUI that displays a table of blocked trading accounts for a given customer.

ViewBlockedStocksFrame.java - GUI that allows a manager to view and unblock stocks.

ViewBlockSellStockTransaction.java - GUI displays transaction history for a particular stock.

ViewPendingAccountFrame.java - GUI for manager to see all pending accounts.

ViewPositionsFrame.java - GUI to view the positions of a trading account.

ViewSellStockTransaction.java - GUI displays the transaction history of a particular stock.

WithdrawPopup.java - a pop-up window that allows the user to withdraw funds from a trading account.

---------------------------------------------------------------------------
# How To Compile
#After unzip the 611FinalProject.zip, navigate to src/main/java/org/TradingSystem/model directory
（Recommend to run it in IntelliJ for full functions）
1. Write a Config file that define the DBPATH, APIKEY and APIHOST

eg. 

    public class Config {
        public static final String DBPATH = "jdbc:sqlite:TradingSystem611/src/main/java/org/TradingSystem/database/tradingSystem.db";
        public static final String APIKEY = "d13a1526bcmsh558712f77440570p17c1e7jsn80e883da4050";
        public static final String APIHOST = "telescope-stocks-options-price-charts.p.rapidapi.com";
   }
2. Build project, run the SystemDriver.java and play around.
