package com.company;

import java.util.*;

public class Repository {

    public static int correctAnswer = 0;

    public static List<Question> questions = new ArrayList<Question>();
    public static List<Command> commands = new ArrayList<Command>();

    public static void preparedQuestion(List questions) {

        questions.add(new Question("Какая страна производит больше всего кофе в мире?", "бразилия"));
        questions.add(new Question("За какую страну играл Дэвид Бекхэм?", "англия"));
        questions.add(new Question("В какой стране находится Прага?", "чехия"));
        questions.add(new Question("Какого цвета была таблетка, которую принимает Нео в фильме “Матрица”?", "красная"));
        questions.add(new Question("Какие животные воспитывали Маугли в “Книге джунглей”?", "волки"));

    }

    public static void preparedCommands(List command){
        commands.add(new Command("help", "Краткая справка обо мне..."));
        commands.add((new Command("play", "Игра начлась!")));
    }
}

