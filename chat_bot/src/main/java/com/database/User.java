package com.database;

import com.State;

public class User {
    private Long chatID;
    private State currentState;
    private String userName;
    private String lastCurrency;
    private double valueInCurrency;
    private double value;


    public User() {

    }

    public User(Long chatID) {
        this.chatID = chatID;
        this.currentState = State.DEFAULT;
    }

    public User(Long chatID, String userName) {
        this.chatID = chatID;
        this.userName = userName;
        this.currentState = State.DEFAULT;
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State nextState) {
        currentState = nextState;
    }

    public Long getChatID() {
        return chatID;
    }

    public String getUserName() {
        return userName;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }


    public void setLastCurrency(String lastCurrency) {
        this.lastCurrency = lastCurrency;
    }

    public String getLastCurrency() {
        return lastCurrency;
    }

    public void setValueInCurrency(double value) {
        this.valueInCurrency = value;
    }

    public double getValueInCurrency() {
        return valueInCurrency;
    }

}
