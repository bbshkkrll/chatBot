package bot;

import bot.database.PostRepo;
import bot.database.UsersRepo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {


    public static void main(String[] args) {
        UsersRepo users = new UsersRepo();
        PostRepo posts = new PostRepo();
        Request parser = new Request();
        Logic logic = new Logic(users, posts, parser);

        Bot bot = new Bot(users, logic, System.getenv("TOKEN"), System.getenv("BOT_NAME"));
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
