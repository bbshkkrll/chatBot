package com.company;

import java.util.Locale;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    SingerRepository singers = new SingerRepository();
    StateRepo states = new StateRepo();

    public String handleUserInput(String userInput, User user) {
        if (user.currentState.state.equals(states.def.state)) {
            if (userInput.equals("help"))
                return commands.help.reference;
            else if (userInput.equals("exit"))
                return commands.exit.reference;
            else if (userInput.equals("/question")) {
                user.nextState(new State("q1"));
            } else
                for (int i = 0; i < singers.singerArr.length; i++)
                    if (userInput.equals(singers.singerArr[i].name.toLowerCase()))
                        return (singers.singerArr[i].songs);
            return "Я такого не знаю.";
        }
        else if (user.currentState.state.equals(states.q1.state)){
            user.nextState(new State("default"));
            if (userInput.equals("шансон"))
                return "Работаем, братья";
            else return "Твоё место у параши, сынок";
        }
        else {
            return "...";
        }
    }
}
