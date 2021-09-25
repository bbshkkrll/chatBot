package com.company;

import java.util.Scanner;

public class InteractionConsole {

    public static void printHelp() {
        System.out.println(CommandRepository.help.reference);
    }

    public static void startQuiz() {
        System.out.println(CommandRepository.play.reference);
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


    public static void printResult(int correctAnswerCount) {
        System.out.println(String.format("Игра окончена! Количество верных ответов %s/5, Молодец!", correctAnswerCount));
    }

    public static void printUnknownCommand() {
        System.out.println("Неизвестная мне команда. Напишите help, чтобы увидеть домтупные команды.");
    }

    public static void printHello() {
        System.out.println("Напишите help, чтобы увидеть домтупные команды.");
    }

    public static String printQuestionGetAnswer(int number, Question question) {
        System.out.println(question.question);

        return new Scanner(System.in).nextLine().toLowerCase();
    }

    public static String printContinueGetAnswer() {
        System.out.println("Заканчиваем? Y/N");

        return new Scanner(System.in).nextLine().toLowerCase();
    }
}
