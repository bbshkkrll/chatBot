package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class Repository {

    public static int correctAnswerCount = 0;

    public static Command[] commandsArr = new Command[]{
            new Command("help", "Краткая справка обо мне..."),
            new Command("play", "Игра начлась!"),
            new Command("exit", "Вы действительно желаете выйти?")
    };

    public static Question[] questionsArr = new Question[]{
            new Question("Какая страна производит больше всего кофе в мире?", "бразилия"),
            new Question("За какую страну играл Дэвид Бекхэм?", "англия"),
            new Question("В какой стране находится Прага?", "чехия"),
            new Question("Какого цвета была таблетка, которую принимает Нео в фильме “Матрица”?", "красная"),
            new Question("Какие животные воспитывали Маугли в “Книге джунглей”?", "волки")
    };
}

