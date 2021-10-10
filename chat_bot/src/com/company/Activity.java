package com.company;

public interface Activity {

    State currentState = new State("default");

    public default void nextState(State state){
    }
}
