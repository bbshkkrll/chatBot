package com.company;

public class CommandsRepository {
    public Command help = new Command("/help", "Бот умеет конвертировать рубли во все популярные валюты. \nЧтобы посмотреть все доступные валюты напиши /currencies");
    public Command exit = new Command("/exit", "Выходим");

    public Command[] commandsArr = new Command[]{
            new Command("/help", "Бот умеет конвертировать рубли во все популярные валюты. \nЧтобы посмотреть все доступные валюты напиши /currencies"),
            new Command("/exit", "Выходим"),
    };

}