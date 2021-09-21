package com.company;

import java.util.Scanner;

public class InteractionConsole {
    public static void printHelp() {
        System.out.println(Repository.commandsArr[0].reference);
    }

    public static void startQuiz() {
        System.out.println(Repository.commandsArr[1].reference);
    }

    public static String printQuestionGetAnswer(int number) {
        System.out.println(Repository.questionsArr[number].question);

        return new Scanner(System.in).nextLine().toLowerCase();
    }

    public static void printTrue() {
        System.out.println("Правильно! +1 балл.");
    }

    public static void printFalse() {
        System.out.println("Ответ не верный! Следующий вопрос:");
    }

    public static void printGameIsStarted() {
        System.out.println("Игра уже идет");
    }

    public static String printContinueGetAnswer() {
        System.out.println("Заканчиваем? Да/Нет");

        return new Scanner(System.in).nextLine().toLowerCase();
    }

    public static void printResult() {
        System.out.println(String.format("Игра окончена! Количество верных ответов %s/5, Молодец!", Repository.correctAnswerCount));
    }
}
