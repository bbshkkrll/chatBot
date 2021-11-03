package com.company;

public class User {
    public Long chatID;
    public State currentState;
    public double value;
    enum State {
        Default,
        GetRate,
        SetValue,
    }

    User(Long chatID) {
        this.chatID = chatID;
        this.currentState = State.Default;
    }

    public void nextState(State nextState) {
        currentState = nextState;
    }
}
