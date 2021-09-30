package com.company;

import java.util.Scanner;

public class Bot {
    Logic Handler = new Logic();

    public void messageHandler() {
        while (true) {
            var userInput = new Scanner(System.in).nextLine().toLowerCase();
            var reply = Handler.handleUserInput(userInput);

            System.out.println(reply);

            if (reply.equals("Выходим"))
                return;
        }
    }
}