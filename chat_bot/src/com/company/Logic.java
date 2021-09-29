package com.company;

import java.util.Locale;

public class Logic {

    CommandsRepository commands = new CommandsRepository();
    SingerRepository singers = new SingerRepository();

    public String handleUserInput(String userInput) {
        if (userInput.equals("help"))
            return commands.help.reference;
        else if (userInput.equals("exit"))
            return commands.exit.reference;
        else
            for (int i = 0; i < singers.singerArr.length; i++)
                if (userInput.equals(singers.singerArr[i].name.toLowerCase()))
                    return (singers.singerArr[i].songs);
        return ("Я такого нэ бачу");
    }
}
