package com.company;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    Logic Handler = new Logic();
    UserRepo users = new UserRepo();

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            var chatID = update.getMessage().getChatId();

            if (!users.userState.containsKey(chatID)) {
                users.userState.put(chatID, new User(chatID));
            }
            SendMessage message = new SendMessage();// Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(Handler.handleUserInput(update.getMessage().getText().toLowerCase(), users.userState.get(chatID)));

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton button = new KeyboardButton();
            KeyboardButton button1 = new KeyboardButton();
            button.setText("Шансон");
            button1.setText("Фонк");
            keyboardRow.add(button);
            keyboardRow.add(button1);
            keyboardRowList.add(keyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            message.setReplyMarkup(replyKeyboardMarkup);

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