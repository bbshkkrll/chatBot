package com.company;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    SingerRepository singers = new SingerRepository();
    ValueParser parser = new ValueParser();

    public String handleUserInput(String userInput, User user) {
        if (user.currentState == User.State.Default) {
            switch (userInput) {
                case "/start":
                    return "Добро пожаловать в обитель шансона!";
                case "/get_artist":
                    user.nextState(User.State.GetArtist);
                    return "Введите название исполнителя \uD83C\uDFA4\uD83C\uDFB5";
                case "/help":
                    return commands.help.reference;
                case "/exit":
                    return commands.exit.reference;
                case "/question":
                    user.nextState(User.State.Question);
                    return commands.getQuestion.reference;
                case "/get_rate":
                    user.nextState(User.State.GetRate);
                    return "\uD83D\uDCB5 Введите название валюты в формате USD \uD83D\uDCB5";
                default:
                    return singers.getSinger(userInput);
            }
        } else if (user.currentState == User.State.Question) {
            user.nextState(User.State.Default);
            if (userInput.equals("шансон") || userInput.equals("фонк"))
                return "Ответ верный";
            else return "Ответ неправильный";
        } else if (user.currentState == User.State.GetArtist) {
            user.nextState(User.State.Default);
            return SearchArtistsExample.searchArtists(userInput);
        } else if (user.currentState == User.State.GetRate) {
            user.nextState(User.State.Default);
            if (userInput.toLowerCase().equals("usd"))
                return "За 1₽ дают " + parser.getRequesrt().rates.USD + "$";
            if (userInput.toLowerCase().equals("eur"))
                return "За 1₽ дают " + parser.getRequesrt().rates.EUR + "€";
            return "Таких валют не знаем";
        } else return "Ушёл в никуда";
    }
}
