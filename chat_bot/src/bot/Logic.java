package bot;

import bot.database.PostRepo;
import bot.database.User;
import bot.database.UsersRepo;
import bot.sheets.SheetsService;

import java.io.IOException;

public class Logic {

    public final UsersRepo users;
    private final PostRepo posts;
    private final CurrencyService currencyService;

    private final SheetsService sheetsService;

    private final String[] defaultCurrencies = new String[]{
            "USD",
            "EUR",
            "JPY",
            "CZK",
            "KZT"
    };

    private final String[] defaultAnswers = new String[]{
            "Да",
            "Нет",
    };

    private final String[] defaultSums = new String[]{
            "100",
            "500",
            "1000",
            "5000",
    };


    public Logic(UsersRepo users, PostRepo posts, SheetsService sheetsService, CurrencyService currencyService) {
        this.users = users;
        this.currencyService = currencyService;
        this.posts = posts;
        this.sheetsService = sheetsService;
    }


    public Reply handleUserInput(String userInput, User user) {
        if (user.getCurrentState() == State.WAIT_VALUE_IN_RUBLES) {
            try {
                var value = Double.parseDouble(userInput);
                if (value <= 0) {
                    return new Reply(defaultSums,
                            "Попробуйте еще раз c другим значением.");
                }
                user.setValue(value);

            } catch (NumberFormatException e) {
                return new Reply(defaultSums,
                        "Некоректный ввод. Попробуйте еще раз");
            }

            user.setCurrentState(State.WAIT_NAME_OF_CURRENCY);
            return new Reply(defaultCurrencies,
                    "Введите международный код валюты, например USD");
        } else if (user.getCurrentState() == State.WAIT_NAME_OF_CURRENCY) {
            try {
                var rates = currencyService.getCurrency();
                if (rates.containsKey(userInput.toUpperCase())) {
                    var valueInCurrency = rates.get(userInput.toUpperCase()) * user.getValue();
                    user.setLastCurrency(userInput.toUpperCase());
                    user.setValueInCurrency(valueInCurrency);

                    if (sheetsService.checkPostExist(valueInCurrency, user.getLastCurrency())) {
                        user.setCurrentState(State.WAIT_ANSWER_TO_QUESTION);
                        return new Reply(defaultAnswers,
                                String.format("За %.2f RUB вы получите %.2f %s", user.getValue(),
                                        valueInCurrency, userInput.toUpperCase()) +
                                        "\s\n Желаете узнать, что можно купить на данную сумму?");
                    } else {
                        user.setCurrentState(State.DEFAULT);
                        return new Reply(null, String.format("За %.2f RUB вы получите %.2f %s", user.getValue(),
                                valueInCurrency, userInput.toUpperCase()));
                    }


                } else {
                    return new Reply(defaultCurrencies,
                            "Я не знаю курс данной валюты. Введите другое название.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new Reply(null,
                        "В данный момент сервис не доступен, попробуйте позже");
            }
        } else if (user.getCurrentState() == State.WAIT_ANSWER_TO_QUESTION) {
            user.setCurrentState(State.DEFAULT);
            if (userInput.equalsIgnoreCase("да")) {
                try {
                    var post = sheetsService.getPostByRandom(user.getValueInCurrency(), user.getLastCurrency());
                    return new Reply(null, post.getTitle());
                } catch (ArrayIndexOutOfBoundsException e) {
                    return new Reply(null,
                            "Такой информации пока нет.");
                }
            } else return new Reply(null,
                    "Обойдемся без фактов.");
        }
        switch (userInput) {
            case "/start":
                return new Reply(null,
                        "Привет! Я знаю актуальные курсы популярных валют и умею конвертировать сумму!\nЧтобы узнать больше напиши /help.");
            case "/help":
                return new Reply(null,
                        "Бот умеет конвертировать рубли во все популярные валюты. \nЧтобы посмотреть все доступные валюты напиши /currencies. \n Чтобы узнать курс валюты напиши /rate и следуй указаниям бота.");
            case "/exit":
                return new Reply(null,
                        "Эта функция пока недоступна. Мы (я) работаем ((работаю)) над этим.");
            case "/rate":
                user.setCurrentState(State.WAIT_VALUE_IN_RUBLES);
                return new Reply(defaultSums,
                        "Введите сумму в рублях (для разделения целой и дробной частей используйте точку)");
            case "/currencies":
                return new Reply(null,
                        "Доступен актуальный курс данных валют:\nAUD, AZN, GBP, AMD, BYN, BGN, BRL, HUF, HKD, DKK, USD, EUR, INR, KZT, CAD, KGS, CNY, MDL, NOK, PLN, RON, XDR, SGD, TJS, TRY, TMT, UZS, UAH, CZK, SEK, CHF, ZAR, KRW, JPY");
            default:
                return new Reply(null,
                        "Я не знаю как на это ответить.");
        }
    }
}
