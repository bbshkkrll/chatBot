package com;

import com.database.PostRepo;
import com.database.UsersRepo;
import com.sheets.SheetServiceUpdater;
import com.sheets.SheetsService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

        SheetServiceUpdater sheetServiceUpdate =
                new SheetServiceUpdater(sheetsService);

        Thread thread = new Thread(sheetServiceUpdate);
        thread.start();

        Logic logic = new Logic(
                users,
                posts,
                sheetsService,
                currencyService);

        Bot bot = new Bot(users, logic, System.getenv("TOKEN"),
                System.getenv("BOT_NAME"));


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
