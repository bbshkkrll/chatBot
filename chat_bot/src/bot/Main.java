package bot;

import bot.database.PostRepo;
import bot.database.User;
import bot.database.UsersRepo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        var users = new UsersRepo();
//        var posts = new PostRepo();
//        posts.initializePosts();
        Bot bot = new Bot(new Logic(users, new RequestParser()), System.getenv("TOKEN"), "@shansonie", users);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
