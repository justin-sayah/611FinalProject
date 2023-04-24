package org.TradingSystem.model;

abstract class Person {
    private int id;
    private String username;
    private String password;
    private String ssn;

    public Person(int id, String username, String password, String ssn){
        this.id = id;
        this.username = username;
        this.password = password;
        this.ssn = ssn;
    }
    public int getID(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getSSN(){
        return ssn;
    }
}
