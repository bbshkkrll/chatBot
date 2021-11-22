package bot;

import bot.database.UsersRepo;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final Logic logic;
    private final String token;
    private final String botName;
    private UsersRepo users;

    public Bot(Logic logic, String token, String botName, UsersRepo users) {
        this.logic = logic;
        this.token = token;
        this.botName = botName;
        this.users = users;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var userName = update.getMessage().getChat().getUserName();
            var chatID = update.getMessage().getChatId();
            var user = users.getUserOrCreate(chatID, userName);

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId()
                    .toString());
            message.setText(logic.handleUserInput(update.getMessage().getText().toLowerCase(), user));

            users.put(user);

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