package com.company;

public class User {
    public Long chatID;

    public State currentState;

    enum State {
        Default,
        GetArtist,
        Question,
    }

    User(Long chatID) {
        this.chatID = chatID;
        this.currentState = State.Default;
    }

    public void nextState(State nextState) {
        currentState = nextState;
    }
}
