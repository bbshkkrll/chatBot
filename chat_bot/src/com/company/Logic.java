package com.company;

import java.util.Locale;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    SingerRepository singers = new SingerRepository();
    StateRepo states = new StateRepo();

    public String handleUserInput(String userInput, User user) {
        if (user.currentState.state.equals(states.def.state)) {
            switch (userInput) {
                case "/help":
                    return commands.help.reference;
                case "/exit":
                    return commands.exit.reference;
                case "/question":
                    user.nextState(new State("q1"));
                    return commands.getQuestion.reference;
                default:
                    for (int i = 0; i < singers.singerArr.length; i++)
                        if (userInput.equals(singers.singerArr[i].name.toLowerCase()))
                            return (singers.singerArr[i].songs);
                    break;
            }
            return "Я такого не знаю.";
        }
        else if (user.currentState.state.equals(states.q1.state)){
            user.nextState(new State("default"));
            if (userInput.equals("шансон"))
                return "Ответ верный";
            else return "Ответ неправильный";
        }
        else {
            return "...";
        }
    }
}
