package com.company;

import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    Logic handler = new Logic();
    UserRepo users = new UserRepo();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            var chatID = update.getMessage().getChatId();

            if (!users.userState.containsKey(chatID)) {
                users.userState.put(chatID, new User(chatID));
            }
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(handler.handleUserInput(update.getMessage().getText().toLowerCase(), users.userState.get(chatID)));

            try {
                execute(message);
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
        return System.getenv("TOKEN");
    }
}