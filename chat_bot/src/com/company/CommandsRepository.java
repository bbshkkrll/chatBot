package com.company;

public class CommandsRepository {
    public Command help = new Command("/help", "Краткая справка обо мне: \nМеня зовут Шансонье. \nОтправь мне имя исполнителя и я скину названия 3 своих любимых треков \nЯ знаю таких исполнителей: \nМихаил Круг, \nАлександр Розенбаум, \nЛесоповал, \nБутырка, \nВоровайки, \nМихаил Шуфутинский. \nПриятного шанснона!");
    public Command exit = new Command("/exit", "Выходим");
    public Command getQuestion = new Command("/question", "Какой твой любимый жанр?");

    public Command[] commandsArr = new Command[]{
            new Command("/help", "Краткая справка обо мне: \nМеня зовут Шансонье. \nОтправь мне имя исполнителя и я скину названия 3 своих любимых треков \nЯ знаю таких исполнителей: \nМихаил Круг, \nАлександр Розенбаум, \nЛесоповал, \nБутырка, \nВоровайки, \nМихаил Шуфутинский. \nПриятного шанснона!"),
            new Command("/exit", "Выходим"),
            new Command("/question", "Какой твой любимый жанр?"),
    };

    public Command getHelp() {
        return help;
    }
}