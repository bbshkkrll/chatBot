package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Repository.preparedCommands(Repository.commands);
        Repository.preparedQuestion(Repository.questions); //переделать

        //доделать справку
        //exit
        //Last question

        DialogueLogic.messageHandler();

    }
}
