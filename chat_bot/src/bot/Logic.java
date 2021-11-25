package bot;

import bot.database.PostRepo;
import bot.database.User;
import bot.database.UsersRepo;

public class Logic {

    public final UsersRepo users;
    private final PostRepo posts;
    private final Request request;

    public Logic(UsersRepo users, PostRepo posts, Request request) {
        this.users = users;
        this.request = request;
        this.posts = posts;
    }


    public String handleUserInput(String userInput, User user) {
        if (user.getCurrentState() == State.WAIT_VALUE_IN_RUBBLES) {
            try {
                var value = Double.parseDouble(userInput);
                if (value <= 0) {
                    user.setCurrentState(State.DEFAULT);
                    return "Некорректный ввод";
                }
                user.setValue(value);

            } catch (NumberFormatException e) {
                user.setCurrentState(State.DEFAULT);
                return "Некоректный ввод";
            }
            user.setCurrentState(State.WAIT_NAME_OF_CURRENCY);
            return "Введите международный код валюты, например USD";
        } else if (user.getCurrentState() == State.WAIT_NAME_OF_CURRENCY) {
            var rates = request.getRequest();
            if (rates.containsKey(userInput.toUpperCase())) {
                user.setCurrentState(State.WAIT_ANSWER_TO_QUESTION);
                var valueInCurrency = rates.get(userInput.toUpperCase()) * user.getValue();
                user.setLastCurrency(userInput.toUpperCase());
                user.setValueInCurrency(valueInCurrency);
                return String.format("За %.2f RUB вы получите %.2f %s", user.getValue(),
                        valueInCurrency, userInput.toUpperCase()) +
                        "\s\n Желаете узнать, что можно купить на данную сумму? (ДА/НЕТ)"; //Здесь появляется клавиатура
            } else {
                user.setCurrentState(State.DEFAULT);
                return "Я не знаю курс данной валюты";
            }
        } else if (user.getCurrentState() == State.WAIT_ANSWER_TO_QUESTION) {
            user.setCurrentState(State.DEFAULT);
            if (userInput.equalsIgnoreCase("да")) {
                var post = posts.getPostByValue(user.getLastCurrency(), user.getValueInCurrency());
                return post.getTitle();
            } else return "Как вам угодно.";
        }
        switch (userInput) {
            case "/start":
                return "Привет! Я знаю актуальные курсы популярных валют и умею конвертировать сумму!\nЧтобы узнать больше напиши /help.";
            case "/help":
                return "Бот умеет конвертировать рубли во все популярные валюты. \nЧтобы посмотреть все доступные валюты напиши /currencies. \n Чтобы узнать курс валюты напиши /rate и следуй указаниям бота.";
            case "/exit":
                return "Эта функция пока недоступна. Мы (я) работаем ((работаю)) над этим.";
            case "/rate":
                user.setCurrentState(State.WAIT_VALUE_IN_RUBBLES);
                return "Введите сумму в рублях (для разделения целой и дробной частей используйте точку)";
            case "/currencies":
                return "Доступен актуальный курс данных валют:\nAUD, AZN, GBP, AMD, BYN, BGN, BRL, HUF, HKD, DKK, USD, EUR, INR, KZT, CAD, KGS, CNY, MDL, NOK, PLN, RON, XDR, SGD, TJS, TRY, TMT, UZS, UAH, CZK, SEK, CHF, ZAR, KRW, JPY";
            default:
                return "Я не знаю как на это ответить.";
        }
    }
}
