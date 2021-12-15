package bot;

import bot.database.User;
import bot.database.UsersRepo;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;

public class Bot extends TelegramLongPollingBot {

    private final Logic logic;
    private final String token;
    private final String botName;
    private final UsersRepo users;

    public Bot(UsersRepo users, Logic logic, String token, String botName) {
        this.logic = logic;
        this.token = token;
        this.botName = botName;
        this.users = users;
    }

    private void sendMessage(User user, Reply reply) {
        SendMessage message = new SendMessage();

        message.setText(reply.getMessage());
        message.setChatId(user.getChatID().toString());
        if (reply.getButtons() != null)
            createKeyboard(message, reply.getButtons());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void createKeyboard(SendMessage message, String[] buttons) {
        ArrayList<ArrayList<String>> keyboard = new ArrayList<>() {
        };
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();


        ArrayList<String> row1 = new ArrayList<>(Arrays.stream(buttons).limit(buttons.length / 2).toList());
        ArrayList<String> row2 = new ArrayList<>(Arrays.stream(buttons).skip(buttons.length / 2).toList());

        keyboard.add(row1);
        keyboard.add(row2);


        for (ArrayList<String> row : keyboard) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String element : row) {
                keyboardRow.add(element);
            }
            keyboardRows.add(keyboardRow);
        }

        ReplyKeyboardMarkup telegramKeyboard = new ReplyKeyboardMarkup();

        message.setReplyMarkup(telegramKeyboard);

        telegramKeyboard.setSelective(true);
        telegramKeyboard.setResizeKeyboard(true);
        telegramKeyboard.setOneTimeKeyboard(true);

        telegramKeyboard.setKeyboard(keyboardRows);

    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userName = update.getMessage().getChat().getUserName();
            Long chatID = update.getMessage().getChatId();
            User user = users.getUserOrCreate(chatID, userName);
            String userInput = update.getMessage().getText().toLowerCase();

            sendMessage(user, logic.handleUserInput(userInput, user));
            users.put(user);

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