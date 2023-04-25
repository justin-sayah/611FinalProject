package org.TradingSystem.model;

abstract class Person {
    private int id;
    private String name;
    private String username;
    private String password;
    private String dob;
    private String ssn;

    public Person(int id, String name, String username, String password, String dob, String ssn){
        setId(id);
        setUsername(username);
        setPassword(password);
        setDob(dob);
        setSsn(ssn);
        setName(name);
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

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }
}
