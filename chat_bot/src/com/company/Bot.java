package com.company;

import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {

    private final Logic logic;
    private final String token;
    private final String botName;
    private HashMap<Long, User> userState;

    public Bot(Logic logic, String token, String botName, HashMap<Long, User> userState) {
        this.logic = logic;
        this.token = token;
        this.botName = botName;
        this.userState = userState;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            var chatID = update.getMessage().getChatId();

            if (!userState.containsKey(chatID)) {
                userState.put(chatID, new User(chatID));
            }
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(logic.handleUserInput(update.getMessage().getText().toLowerCase(), userState.get(chatID)));

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}