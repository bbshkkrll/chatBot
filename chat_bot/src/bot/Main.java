package bot;

import bot.database.Post;
import bot.database.PostRepo;
import bot.database.UsersRepo;
import bot.sheets.SheetsService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.imageio.IIOException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws GeneralSecurityException, IOException {

        UsersRepo users = new UsersRepo(
                System.getenv("MONGO_URI"));
        PostRepo posts = new PostRepo(
                System.getenv("MONGO_URI"));

        CurrencyService currencyService =
                new CurrencyService(
                        System.getenv("CURRENCY_URI"));

        SheetsService sheetsService =
                new SheetsService(
                        System.getenv("APPLICATION_NAME"),
                        System.getenv("SPREADSHEET_ID"));

        Logic logic = new Logic(
                users,
                posts,
                sheetsService,
                currencyService);


        Bot bot = new Bot(users, logic,
                System.getenv("TOKEN"),
                System.getenv("BOT_NAME"));

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
