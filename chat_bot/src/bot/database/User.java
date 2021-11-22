package bot.database;

import bot.State;

public class User {
    public Long chatID;
    public State currentState;
    public double value;
    public String userName;

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
//
//    public void setNextState(State nextState) {
//        currentState = nextState;
//    }

}
