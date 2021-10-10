package com.company;

public class User implements Activity {
    public Long chatID;

    public State currentState;


    User(Long chatID){
        this.chatID = chatID;
        this.currentState = new State("default");
    }

    @Override
    public void nextState(State nextState) {
        currentState = nextState;
    }
}
