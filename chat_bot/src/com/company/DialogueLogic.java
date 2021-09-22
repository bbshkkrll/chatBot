package com.company;

import java.util.Scanner;

public class DialogueLogic {

    public static void messageHandler() {


        InteractionConsole.printHello();

        bot:
        while (true) {
            var userInput = new Scanner(System.in).nextLine().toLowerCase();

            if (userInput.equals("help")) {
                InteractionConsole.printHelp();
            } else if (userInput.equals("exit")) {
                if (InteractionConsole.printContinueGetAnswer().equals("да")) {
                    break bot;
                }
            } else if (userInput.equals("play")) {
                InteractionConsole.startQuiz();
                quiz:
                for (var i = 0; i < Repository.questionsArr.length; i++) {
                    var userAnswer = InteractionConsole.printQuestionGetAnswer(i);
                    if (userAnswer.equals(Repository.questionsArr[i].answer)) {
                        InteractionConsole.printTrue();
                        Repository.correctAnswerCount++;
                    } else if (userAnswer.equals("play")) {
                        InteractionConsole.printGameIsStarted();
                        i--;
                    } else if (userAnswer.equals("exit")) {
                        if (InteractionConsole.printContinueGetAnswer().equals("нет")) {
                            i--;
                            continue;
                        } else break bot;
                    } else if (userAnswer.equals("help")) {
                        InteractionConsole.printHelp();
                        if (InteractionConsole.printContinueGetAnswer().equals("нет")) {
                            i--;
                            continue;
                        } else break quiz;
                    } else InteractionConsole.printFalse();
                }

                InteractionConsole.printResult();
            } else {
                InteractionConsole.printUnknownCommand();
            }
        }
    }
}
