package com.company;

public class User {
    public Long chatID;
    private State currentState;
    public double value;

    User(Long chatID) {
        this.chatID = chatID;
        this.currentState = State.DEFAULT;
    }

    public State getCurrentState(){
        return currentState;
    }

    public void setNextState(State nextState) {
        currentState = nextState;
    }

}
