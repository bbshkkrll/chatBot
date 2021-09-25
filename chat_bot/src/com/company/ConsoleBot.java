package com.company;

import java.util.Scanner;

public class ConsoleBot {

    public static void messageHandler() {


        InteractionConsole.printHello();

        bot:
        while (true) {
            var userInput = new Scanner(System.in).nextLine().toLowerCase();

            var reply = Logic.handleUserInput(userInput);
            // output
        }
    }
}
