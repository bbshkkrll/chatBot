package com.company;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    SingerRepository singers = new SingerRepository();

    public String handleUserInput(String userInput, User user) {
        if (user.currentState == User.State.Default) {
            switch (userInput) {
                case "/help":
                    return commands.help.reference;
                case "/exit":
                    return commands.exit.reference;
                case "/question":
                    user.nextState(User.State.Question);
                    return commands.getQuestion.reference;
                default:
                    return singers.getSinger(userInput);
            }
        } else if (user.currentState == User.State.Question) {
            user.nextState(User.State.Default);
            if (userInput.equals("шансон") || userInput.equals("фонк"))
                return "Ответ верный";
            else return "Ответ неправильный";
        } else {
            return "...";
        }
    }
}
