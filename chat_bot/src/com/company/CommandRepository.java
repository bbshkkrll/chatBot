package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class CommandRepository {

    public static Command help = new Command("help", "Краткая справка обо мне...");
    public static Command play = new Command("play", "Игра начлась!");
    public static Command exit = new Command("exit", "Вы действительно желаете выйти?");

    public int correctAnswerCount = 0;

    public Command[] commandsArr = new Command[]{
            new Command("help", "Краткая справка обо мне..."),
            new Command("play", "Игра начлась!"),
            new Command("exit", "Вы действительно желаете выйти?")
    };

}

