package com.company;

import java.util.Locale;
import java.util.Scanner;

public class InteractionConsole {
    public static void printHelp() {
        System.out.println(Repository.commands.get(0).reference);
    }

    public static void startQuiz() {
        System.out.println(Repository.commands.get(1).reference);
    }

    public static String printQuestionGetAnswer(int number) {
        System.out.println(Repository.questions.get(number).question);

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
        System.out.println("Продолжаем? Да/Нет");

        return new Scanner(System.in).nextLine().toLowerCase();
    }

    public static void printResult() {
        System.out.println("Игра окончена! Количество верных ответов " + Repository.correctAnswer + "/5" + " Молоде *A*Y*E*" );
    }
}
