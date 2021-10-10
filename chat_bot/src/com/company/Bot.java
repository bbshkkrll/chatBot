package com.company;

import java.util.Locale;
import java.util.Scanner;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    Logic Handler = new Logic();
    UserRepo users = new UserRepo();

    public void messageHandler() {
        while (true) {

            /*var userInput = new Scanner(System.in).nextLine().toLowerCase();
            var reply = Handler.handleUserInput(userInput);

            System.out.println(reply);

            if (reply.equals("Выходим"))
                return;*/
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            var User = new User(update.getMessage().getChatId());
            if (!users.userState.containsKey(User.chatID)){
                users.userState.put(User.chatID, User);
            }
            SendMessage message = new SendMessage();// Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(Handler.handleUserInput(update.getMessage().getText().toLowerCase(), User));


//            SendMessage message1 = new SendMessage();
//            message1.setChatId("272975891");
//            message1.setText(update.getMessage().getFrom().getUserName().toString() +  ": " + update.getMessage().getText());

            try {
                execute(message);
               // execute(message1);// Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "shansonie";
    }

    @Override
    public String getBotToken() {
        return "2009091134:AAHLfvCwMuDmnrDTFEv7ha2JwwVbnNXD-4Q";
    }
}