package com.company;

import java.util.Scanner;

public class DialogueLogic {

    public static void messageHandler() {

        while (true) {
            var userInput = new Scanner(System.in).nextLine().toLowerCase();

            if (userInput.equals("help")) {
                InteractionConsole.printHelp();
            }

            if (userInput.equals("play")) {
                InteractionConsole.startQuiz();
                quiz:
                for (var i = 0; i < Repository.questions.size(); i++) {
                    var userAnswer = InteractionConsole.printQuestionGetAnswer(i);
                    if (userAnswer.equals(Repository.questions.get(i).answer)) {

                        InteractionConsole.printTrue();
                        Repository.correctAnswer++;
                    } else if (userAnswer.equals("play")) {
                        InteractionConsole.printGameIsStarted();
                        i--;
                    } else if (userAnswer.equals("help")) {
                        InteractionConsole.printHelp();
                        if (InteractionConsole.printContinueGetAnswer().toLowerCase().equals("да")) {
                            i--;
                            continue;
                        } else break quiz;
                    } else InteractionConsole.printFalse();
                }

                InteractionConsole.printResult();
            }
        }
    }
}
